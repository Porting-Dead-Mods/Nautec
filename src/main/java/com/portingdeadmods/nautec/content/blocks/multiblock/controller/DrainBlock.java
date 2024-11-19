package com.portingdeadmods.nautec.content.blocks.multiblock.controller;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.ContainerBlock;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import com.portingdeadmods.nautec.utils.ItemUtils;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class DrainBlock extends ContainerBlock {
    public DrainBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(DrainMultiblock.FORMED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(DrainMultiblock.DRAIN_PART, Multiblock.FORMED));
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.DRAIN.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DrainBlock::new);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState p_60503_, Level level, BlockPos pos, Player player, BlockHitResult p_60508_) {
        if (level.getBlockEntity(pos) instanceof DrainBlockEntity drainBlockEntity ) {
            if (player.isShiftKeyDown() && !drainBlockEntity.isClosing()) {
                drainBlockEntity.close();
                return InteractionResult.SUCCESS;
            } else  if (p_60503_.getValue(Multiblock.FORMED)) {
                ItemStack stack = player.getMainHandItem();
                IFluidHandler itemFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);
                if (itemFluidHandler != null) {
                    extractFluid(player, level, InteractionHand.MAIN_HAND, (FluidTank) drainBlockEntity.getFluidHandler(), itemFluidHandler);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.useWithoutItem(p_60503_, level, pos, player, p_60508_);
    }


    private static void extractFluid(Player player, Level level, InteractionHand interactionHand, FluidTank fluidHandler, IFluidHandler fluidHandlerItem) {
        FluidStack fluidInTank = fluidHandler.getFluidInTank(0);
        if (player.getItemInHand(interactionHand).is(Items.BUCKET)) {
            player.getItemInHand(interactionHand).shrink(1);
            ItemUtils.giveItemToPlayerNoSound(player, fluidInTank.getFluid().getBucket().getDefaultInstance());
            if (fluidInTank.is(Fluids.WATER)) {
                level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 0.8F, 1.0F);
            } else if (fluidInTank.is(Fluids.LAVA)) {
                level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BUCKET_FILL_LAVA, SoundSource.PLAYERS, 0.8F, 1.0F);
            }
            fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE);
        } else {
            FluidStack fluidStack = fluidHandler.drain(fluidHandler.getFluidInTank(0).getAmount(), IFluidHandler.FluidAction.EXECUTE);
            int remainderAmount = fluidHandlerItem.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
            FluidStack newFluidStack = fluidStack.copy();
            newFluidStack.setAmount(remainderAmount);
            fluidHandler.setFluid(newFluidStack);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            MultiblockHelper.unform(NTMultiblocks.DRAIN.get(), pos, level, null);
            level.removeBlock(pos.above(), false);
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

}
