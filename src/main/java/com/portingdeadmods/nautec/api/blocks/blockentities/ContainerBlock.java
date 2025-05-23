package com.portingdeadmods.nautec.api.blocks.blockentities;

import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.FakeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ContainerBlock extends BaseEntityBlock {
    public ContainerBlock(Properties properties) {
        super(properties);
    }

    public abstract boolean tickingEnabled();

    public abstract BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType();

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return getBlockEntityType().create(blockPos, blockState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!tickingEnabled()) return null;

        return createTickerHelper(blockEntityType, getBlockEntityType(), (level1, pos1, state1, entity1) -> entity1.commonTick());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ContainerBlockEntity containerBE) {
            if (!state.is(newState.getBlock())) {
                containerBE.drop();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MenuProvider menuProvider) {
            BlockPos pos1 = pos;
            if (blockEntity instanceof FakeBlockEntity fakeBlockEntity && fakeBlockEntity.getActualBlockEntityPos() != null) {
                pos1 = fakeBlockEntity.getActualBlockEntityPos();
            }
            player.openMenu(menuProvider, pos1);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}