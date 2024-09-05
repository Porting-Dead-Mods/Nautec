package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.items.IFluidItem;
import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.ItemPowerWrapper;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = ModJam.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class CapabilityAttachEvent {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        registerItemCaps(event);
        registerBECaps(event);
        registerEntityCaps(event);
    }

    private static void registerEntityCaps(RegisterCapabilitiesEvent event){
        // TODO: Register Player Augmentation Capability
    }

    private static void registerItemCaps(RegisterCapabilitiesEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof IPowerItem powerItem) {
                event.registerItem(MJCapabilities.PowerStorage.ITEM, (stack, ctx) -> new ItemPowerWrapper(stack, powerItem), item);
            }

            if (item instanceof IFluidItem fluidItem) {
                event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidHandlerItemStack(MJDataComponents.FLUID, stack, fluidItem.getFluidCapacity()), item);
            }
        }
    }

    private static void registerBECaps(RegisterCapabilitiesEvent event) {
        for (DeferredHolder<BlockEntityType<?>, ? extends BlockEntityType<?>> be : MJBlockEntityTypes.BLOCK_ENTITIES.getEntries()) {
            Block validBlock = be.get().getValidBlocks().stream().iterator().next();
            BlockEntity testBE = be.get().create(BlockPos.ZERO, validBlock.defaultBlockState());
            if (testBE instanceof ContainerBlockEntity containerBE) {
                if (containerBE.getPowerStorage() != null) {
                    event.registerBlockEntity(MJCapabilities.PowerStorage.BLOCK, be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getPowerStorage());
                }

                if (containerBE.getItemHandler() != null) {
                    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getItemHandlerOnSide(dir));
                }

                if (containerBE.getFluidHandler() != null) {
                    event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getFluidHandlerOnSide(dir));
                }
            }
        }
    }

}
