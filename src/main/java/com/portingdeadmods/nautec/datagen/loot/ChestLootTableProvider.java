package com.portingdeadmods.nautec.datagen.loot;

import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.registries.NTLootTables;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ChestLootTableProvider implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> writer) {
        writer.accept(NTLootTables.CRATE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4.0F, 5.0F))
                        .add(LootItem.lootTableItem(NTItems.BURNT_COIL.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(NTItems.BROWN_POLYMER.get()).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(NTItems.CAST_IRON_INGOT.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(NTItems.AQUARINE_STEEL_COMPOUND.get()).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))))
                        .add(LootItem.lootTableItem(NTItems.ATLANTIC_GOLD_NUGGET.get()).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.KELP).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.SEA_PICKLE).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.BUBBLE_CORAL).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.DEAD_BRAIN_CORAL).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.DEAD_BRAIN_CORAL_FAN).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                )
        );
    }
}
