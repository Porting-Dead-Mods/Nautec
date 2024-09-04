package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModJam.MODID);

    public static final Supplier<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("modjam.creative_tab.main"))
            .icon(() -> Blocks.DARK_PRISMARINE.asItem().getDefaultInstance())
            .displayItems((params, output) -> {
                output.accept(MJItems.EXAMPLE_POWER_ITEM);
            })
            .build());
}
