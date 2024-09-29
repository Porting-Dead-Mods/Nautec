package com.portingdeadmods.nautec;

import com.mojang.logging.LogUtils;
import com.portingdeadmods.nautec.compat.duradisplay.DuraDisplayCompat;
import com.portingdeadmods.nautec.content.items.BatteryItem;
import com.portingdeadmods.nautec.content.items.PrismMonocleItem;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.registries.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

@Mod(Nautec.MODID)
public final class Nautec {
    public static final Minecraft instance = Minecraft.getInstance();
    public static final String MODID = "nautec";
    public static final String MODNAME = "NauTec";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Nautec(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(NewRegistryEvent.class, event -> {
            event.register(NTRegistries.MULTIBLOCK);
            event.register(NTRegistries.AUGMENT_SLOT);
            event.register(NTRegistries.AUGMENT_TYPE);
        });

        NTEntites.ENTITIES.register(modEventBus);
        NTItems.ITEMS.register(modEventBus);
        NTFluids.FLUIDS.register(modEventBus);
        NTBlocks.BLOCKS.register(modEventBus);
        NTRecipes.SERIALIZERS.register(modEventBus);
        NTFluidTypes.FLUID_TYPES.register(modEventBus);
        NTDataAttachments.ATTACHMENTS.register(modEventBus);
        NTArgumentTypes.ARGUMENT_TYPES.register(modEventBus);
        NTBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        NTCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        NTDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        NTAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        NTMultiblocks.MULTIBLOCKS.register(modEventBus);
        NTAugments.AUGMENTS.register(modEventBus);
        NTAugmentSlots.AUGMENT_SLOTS.register(modEventBus);
        NTMenuTypes.MENUS.register(modEventBus);
        NTStructures.STRUCTURES.register(modEventBus);
        NTLootModifier.LOOT_MODIFIERS.register(modEventBus);

        modEventBus.addListener(PrismMonocleItem::registerCapabilities);
        modEventBus.addListener(BatteryItem::registerCapabilities);

        modContainer.registerConfig(ModConfig.Type.COMMON, NTConfig.SPEC);

        if (ModList.get().isLoaded("duradisplay")) {
            DuraDisplayCompat.register();
        }
    }
}
