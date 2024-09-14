package com.portingdeadmods.modjam;

import com.mojang.logging.LogUtils;
import com.portingdeadmods.modjam.content.items.PrismMonocleItem;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.registries.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import java.util.Random;

@Mod(ModJam.MODID)
public final class ModJam {
    public static final String MODID = "modjam";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Random random = new Random();

    public ModJam(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(NewRegistryEvent.class, event -> {
            event.register(MJRegistries.MULTIBLOCK);
            event.register(MJRegistries.AUGMENT);
        });

        MJEntites.ENTITIES.register(modEventBus);
        MJItems.ITEMS.register(modEventBus);
        MJFluids.FLUIDS.register(modEventBus);
        MJBlocks.BLOCKS.register(modEventBus);
        MJRecipes.SERIALIZERS.register(modEventBus);
        MJFluidTypes.FLUID_TYPES.register(modEventBus);
        MJDataAttachments.ATTACHMENTS.register(modEventBus);
        MJBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        MJCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        MJDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        MJMultiblocks.MULTIBLOCKS.register(modEventBus);
        MJAugments.AUGMENTS.register(modEventBus);
        MJMenuTypes.MENUS.register(modEventBus);
        MJStructures.STRUCTURES.register(modEventBus);
        MJLootModifier.LOOT_MODIFIERS.register(modEventBus);

        modEventBus.addListener(PrismMonocleItem::registerCapabilities);

        modContainer.registerConfig(ModConfig.Type.COMMON, MJConfig.SPEC);
    }
}
