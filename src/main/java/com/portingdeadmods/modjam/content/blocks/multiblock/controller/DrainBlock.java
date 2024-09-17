package com.portingdeadmods.modjam.content.blocks.multiblock.controller;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.blockentities.ContainerBlock;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.modjam.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.registries.MJMultiblocks;
import com.portingdeadmods.modjam.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
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
        return MJBlockEntityTypes.DRAIN.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DrainBlock::new);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState p_60503_, Level level, BlockPos pos, Player player, BlockHitResult p_60508_) {
        if (player.isShiftKeyDown() && level.getBlockEntity(pos) instanceof DrainBlockEntity drainBlockEntity && !drainBlockEntity.isClosing()) {
            drainBlockEntity.close();
        }
        return super.useWithoutItem(p_60503_, level, pos, player, p_60508_);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            MultiblockHelper.unform(MJMultiblocks.DRAIN.get(), pos, level, null);
            level.removeBlock(pos.above(), false);
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

}
