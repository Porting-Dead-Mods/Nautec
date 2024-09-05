package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.IPowerBE;
import com.portingdeadmods.modjam.api.items.IFluidItem;
import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.ItemPowerWrapper;
import com.portingdeadmods.modjam.data.MJDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

@EventBusSubscriber(modid = ModJam.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class CapabilityAttachEvent {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        registerItemCaps(event);
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
        for (BlockEntityType<?> be : BuiltInRegistries.BLOCK_ENTITY_TYPE) {
            Block validBlock = be.getValidBlocks().stream().iterator().next();
            BlockEntity actualBE = be.create(BlockPos.ZERO, validBlock.defaultBlockState());
            if (actualBE instanceof IPowerBE powerBE) {
            }
        }
    }

}
