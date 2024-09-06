package com.portingdeadmods.modjam;

import com.portingdeadmods.modjam.content.items.PrismMonocleItem;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.registries.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ModJam.MODID)
public final class ModJam {
    public static final String MODID = "modjam";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModJam(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(NewRegistryEvent.class, event -> event.register(MJRegistries.MULTIBLOCK));

        MJItems.ITEMS.register(modEventBus);
        MJBlocks.BLOCKS.register(modEventBus);
        MJDataAttachments.ATTACHMENTS.register(modEventBus);
        MJFluids.FLUIDS.register(modEventBus);
        MJFluidTypes.FLUID_TYPES.register(modEventBus);
        MJCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        MJDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        MJBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);

        modEventBus.addListener(PrismMonocleItem::registerCapabilities);

        modContainer.registerConfig(ModConfig.Type.COMMON, MJConfig.SPEC);
    }
}
