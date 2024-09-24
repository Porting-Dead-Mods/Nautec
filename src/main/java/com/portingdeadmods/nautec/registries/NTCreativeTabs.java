package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nautec.MODID);

    public static final Supplier<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("nautec.creative_tab.main"))
            .icon(() -> Blocks.DARK_PRISMARINE.asItem().getDefaultInstance())
            .displayItems((params, output) -> {
                for (ItemLike item : NTItems.CREATIVE_TAB_ITEMS) {
                    output.accept(item);
                    if (item instanceof IPowerItem) {
                        addPowered(output, item.asItem());
                    }
                }

                output.accept(NTItems.SALT_WATER_BUCKET);
                output.accept(NTItems.EAS_BUCKET);
                output.accept(NTItems.ETCHING_ACID_BUCKET);
            })
            .build());

    // FIXME: Not working :sob:
    public static void addPowered(CreativeModeTab.Output output, Item item) {
        ItemStack itemStack = new ItemStack(item);
        ComponentPowerStorage powerStorage = itemStack.getOrDefault(NTDataComponents.POWER, ComponentPowerStorage.EMPTY);
        itemStack.set(NTDataComponents.POWER, new ComponentPowerStorage(powerStorage.powerCapacity(), powerStorage.powerCapacity(), 0));
        output.accept(itemStack);
    }
}
