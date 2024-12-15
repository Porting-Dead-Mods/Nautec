package com.portingdeadmods.nautec;

import com.mojang.logging.LogUtils;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.items.IBacteriaItem;
import com.portingdeadmods.nautec.api.items.ICurioItem;
import com.portingdeadmods.nautec.api.items.IFluidItem;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.ItemBacteriaWrapper;
import com.portingdeadmods.nautec.capabilities.power.ItemPowerWrapper;
import com.portingdeadmods.nautec.compat.duradisplay.DuraDisplayCompat;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentTypeArgumentType;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.Objects;
import java.util.stream.Collectors;

@Mod(Nautec.MODID)
public final class Nautec {
    public static final String MODID = "nautec";
    public static final String MODNAME = "NauTec";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Nautec(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(NewRegistryEvent.class, event -> {
            event.register(NTRegistries.MULTIBLOCK);
            event.register(NTRegistries.AUGMENT_SLOT);
            event.register(NTRegistries.AUGMENT_TYPE);
        });

        modEventBus.addListener(DataPackRegistryEvent.NewRegistry.class, event -> {
            event.dataPackRegistry(NTRegistries.BACTERIA_KEY, Bacteria.CODEC);
        });

        NTEntities.ENTITIES.register(modEventBus);
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

        modEventBus.addListener(this::onRegisterAugments);
        modEventBus.addListener(this::registerCapabilities);

        modContainer.registerConfig(ModConfig.Type.COMMON, NTConfig.SPEC);

        if (ModList.get().isLoaded("duradisplay")) {
            DuraDisplayCompat.register();
        }
    }

    private void onRegisterAugments(RegisterEvent event) {
        Registry<AugmentSlot> slotRegistry = event.getRegistry(NTRegistries.AUGMENT_SLOT.key());
        if (slotRegistry != null) {
            AugmentSlotArgumentType.suggestions = slotRegistry.keySet().stream().map(Objects::toString).collect(Collectors.toSet());
        }

        Registry<AugmentType<?>> augmentRegistry = event.getRegistry(NTRegistries.AUGMENT_TYPE.key());
        if (augmentRegistry != null) {
            AugmentTypeArgumentType.suggestions = augmentRegistry.keySet().stream().map(Objects::toString).collect(Collectors.toSet());
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        registerItemCaps(event);
        registerBECaps(event);
    }

    private static void registerItemCaps(RegisterCapabilitiesEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof IPowerItem powerItem) {
                event.registerItem(NTCapabilities.PowerStorage.ITEM, (stack, ctx) -> new ItemPowerWrapper(stack, powerItem), item);
            }

            if (item instanceof IFluidItem fluidItem) {
                event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidHandlerItemStack(NTDataComponents.FLUID, stack, fluidItem.getFluidCapacity()), item);
            }

            if (item instanceof IBacteriaItem) {
                event.registerItem(NTCapabilities.BacteriaStorage.ITEM, (stack, ctx) -> stack.get(NTDataComponents.BACTERIA).isPresent()
                                ? new ItemBacteriaWrapper(NTDataComponents.BACTERIA, stack)
                                : null,
                        item);
            }

            if (item instanceof ICurioItem curioItem) {
                event.registerItem(CuriosCapability.ITEM,
                        (stack, context) -> new ICurio() {
                            @Override
                            public ItemStack getStack() {
                                return stack;
                            }

                            @Override
                            public void curioTick(SlotContext slotContext) {
                                curioItem.curioTick(stack, slotContext);
                            }
                        }, item);
            }
        }
    }

    private static void registerBECaps(RegisterCapabilitiesEvent event) {
        for (DeferredHolder<BlockEntityType<?>, ? extends BlockEntityType<?>> be : NTBlockEntityTypes.BLOCK_ENTITIES.getEntries()) {
            Block validBlock = be.get().getValidBlocks().stream().iterator().next();
            BlockEntity testBE = be.get().create(BlockPos.ZERO, validBlock.defaultBlockState());
            if (testBE instanceof ContainerBlockEntity containerBE) {
                if (containerBE.getPowerStorage() != null) {
                    event.registerBlockEntity(NTCapabilities.PowerStorage.BLOCK, be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getPowerStorage());
                }

                if (containerBE.getItemHandler() != null) {
                    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getItemHandlerOnSide(dir));
                }

                if (containerBE.getFluidHandler() != null) {
                    event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getFluidHandlerOnSide(dir));
                }

                if (containerBE.getBacteriaStorage() != null){
                    event.registerBlockEntity(NTCapabilities.BacteriaStorage.BLOCK, be.get(), (blockEntity, ctx) -> ((ContainerBlockEntity) blockEntity).getBacteriaStorage());
                }
            }
        }
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
