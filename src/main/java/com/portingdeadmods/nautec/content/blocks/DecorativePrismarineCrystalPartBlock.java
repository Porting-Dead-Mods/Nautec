package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DecorativePrismarineCrystalPartBlock extends Block implements SimpleWaterloggedBlock {
    public static final IntegerProperty INDEX = IntegerProperty.create("index", 0, 5);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape BOTTOM_SHAPE = Block.box(2, 4, 2, 14, 16, 14);
    public static final VoxelShape TOP_SHAPE = Block.box(2, 0, 2, 14, 12, 14);

    public DecorativePrismarineCrystalPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(INDEX, 0).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INDEX, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(INDEX) == 5) {
            return BOTTOM_SHAPE;
        } else if (state.getValue(INDEX) == 0) {
            return TOP_SHAPE;
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return simpleCodec(DecorativePrismarineCrystalPartBlock::new);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        // Find the main decorative crystal block (should be at the bottom)
        int currentIndex = state.getValue(INDEX);
        BlockPos bottomPos = pos.below(currentIndex);
        BlockState bottomState = level.getBlockState(bottomPos);
        if (bottomState.getBlock() instanceof DecorativePrismarineCrystalBlock) {
            DecorativePrismarineCrystalBlock.removeCrystal(level, player, bottomPos);
        }
        return true;
    }
}