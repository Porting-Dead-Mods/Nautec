package com.portingdeadmods.nautec.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddItemModifier extends LootModifier {

    public static Supplier<MapCodec<AddItemModifier>> CODEC_SUPPLIER = Suppliers.memoize(() -> RecordCodecBuilder
            .mapCodec(instance -> AddItemModifier.codecStart(instance)
            .and(ItemStack.CODEC.fieldOf("item")
                    .forGetter(addItemModifierInstance -> addItemModifierInstance.item))
            .apply(instance, AddItemModifier::new)));

    private final ItemStack item;

    public AddItemModifier(LootItemCondition[] conditionsIn, ItemStack item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }

        generatedLoot.add(item);
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC_SUPPLIER.get();
    }
}