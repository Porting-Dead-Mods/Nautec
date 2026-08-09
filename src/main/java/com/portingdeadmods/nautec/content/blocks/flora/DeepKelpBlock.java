package com.portingdeadmods.nautec.content.blocks.flora;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DeepKelpBlock extends GrowingPlantHeadBlock implements LiquidBlockContainer {
    public static final MapCodec<DeepKelpBlock> CODEC = simpleCodec(DeepKelpBlock::new);
    private static final VoxelShape SHAPE = Block.column(16.0, 0.0, 9.0);

    public DeepKelpBlock(BlockBehaviour.Properties properties) {
        super(properties, Direction.UP, SHAPE, true, 0.14);
    }

    @Override
    protected @NotNull MapCodec<DeepKelpBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (strandHeight(level, pos) >= NTConfig.kelpHeight) {
            return;
        }
        super.randomTick(state, level, pos, random);
    }

    private int strandHeight(ServerLevel level, BlockPos pos) {
        int height = 1;
        BlockPos.MutableBlockPos cursor = pos.mutable().move(Direction.DOWN);
        while (level.getBlockState(cursor).is(NTBlocks.DEEP_KELP_PLANT.get())) {
            height++;
            cursor.move(Direction.DOWN);
        }
        return height;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.is(Blocks.WATER);
    }

    @Override
    protected @NotNull Block getBodyBlock() {
        return NTBlocks.DEEP_KELP_PLANT.get();
    }

    @Override
    protected boolean canAttachTo(BlockState state) {
        return !state.is(BlockTags.CANNOT_SUPPORT_KELP);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity user, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid type) {
        return false;
    }

    @Override
    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidState) {
        return false;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(@NotNull RandomSource random) {
        return 1;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return fluidState.is(FluidTags.WATER) && fluidState.isFull() ? super.getStateForPlacement(context) : null;
    }

    @Override
    protected @NotNull FluidState getFluidState(@NotNull BlockState state) {
        return Fluids.WATER.getSource(false);
    }
}
