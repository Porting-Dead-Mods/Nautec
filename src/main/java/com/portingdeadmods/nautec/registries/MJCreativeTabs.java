package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nautec.MODID);

    public static final Supplier<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("nautec.creative_tab.main"))
            .icon(() -> Blocks.DARK_PRISMARINE.asItem().getDefaultInstance())
            .displayItems((params, output) -> {
                for (ItemLike item : MJItems.CREATIVE_TAB_ITEMS) {
                    output.accept(item);
                }

                output.accept(MJItems.SALT_WATER_BUCKET);
                output.accept(MJItems.EAS_BUCKET);
                output.accept(MJItems.ETCHING_ACID_BUCKET);
            })
            .build());
}
