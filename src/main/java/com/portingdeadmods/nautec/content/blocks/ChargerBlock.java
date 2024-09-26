package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.SimpleMapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.api.utils.HorizontalDirection;
import com.portingdeadmods.nautec.content.blockentities.ChargerBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class ChargerBlock extends LaserBlock {
    public ChargerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.CHARGER.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ChargerBlock::new);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof ChargerBlockEntity be) {
            IItemHandler itemHandler = be.getItemHandler();

            Direction clickedFace = player.getDirection();
            if (stack.isEmpty()) {
                return extractItemsSided(player, itemHandler, clickedFace);
            } else {
                return insertItemsSided(stack, player, hand, itemHandler, clickedFace);
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private ItemInteractionResult extractItemsSided(Player player, IItemHandler itemHandler, Direction clickedFace) {
        ItemStack stackInSlot = itemHandler.getStackInSlot(0);
        ItemStack itemStack = itemHandler.extractItem(0, stackInSlot.getMaxStackSize(), false);
        if (!itemStack.isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, itemStack);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private ItemInteractionResult insertItemsSided(ItemStack stack, Player player, InteractionHand hand, IItemHandler itemHandler, Direction clickedFace) {
        ItemStack stackInSlot = itemHandler.getStackInSlot(0);

        if (canInsert(stack, itemHandler, stackInSlot, 0)) {
            ItemStack itemStack = itemHandler.insertItem(0, stack, false);
            player.setItemInHand(hand, itemStack);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static boolean canInsert(ItemStack stack, IItemHandler itemHandler, ItemStack stackInSlot, int slot) {
        return stackInSlot.isEmpty()
                || (stackInSlot.is(stack.getItem())
                && stack.getCount() + stackInSlot.getCount() <= Math.min(itemHandler.getSlotLimit(slot), stack.getMaxStackSize()));
    }

}
