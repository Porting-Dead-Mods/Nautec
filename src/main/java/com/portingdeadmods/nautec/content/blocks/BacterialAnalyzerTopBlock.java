package com.portingdeadmods.nautec.content.blocks;

import com.portingdeadmods.nautec.content.blockentities.BacterialAnalyzerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BacterialAnalyzerTopBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE_N = Stream.of(
            Block.box(5, 0, 5, 11, 2, 12),
            Block.box(7, 2, 10, 9, 12, 12),
            Block.box(7, 4, 7, 9, 5, 9),
            Block.box(6, 5, 6, 10, 9, 10)
    ).reduce(Shapes::or).get();
    public static final VoxelShape SHAPE_E = Stream.of(
            Block.box(4, 0, 5, 11, 2, 11),
            Block.box(4, 2, 7, 6, 12, 9),
            Block.box(7, 4, 7, 9, 5, 9),
            Block.box(6, 5, 6, 10, 9, 10),
            Block.box(7, 5, 7, 9, 9, 9)
    ).reduce(Shapes::or).get();
    public static final VoxelShape SHAPE_S = Stream.of(
            Block.box(5, 0, 4, 11, 2, 11),
            Block.box(7, 2, 4, 9, 12, 6),
            Block.box(7, 4, 7, 9, 5, 9),
            Block.box(6, 5, 6, 10, 9, 10),
            Block.box(7, 5, 7, 9, 9, 9)
    ).reduce(Shapes::or).get();
    public static final VoxelShape SHAPE_W = Stream.of(
            Block.box(5, 0, 5, 12, 2, 11),
            Block.box(10, 2, 7, 12, 12, 9),
            Block.box(7, 4, 7, 9, 5, 9),
            Block.box(6, 5, 6, 10, 9, 10),
            Block.box(7, 5, 7, 9, 9, 9)
    ).reduce(Shapes::or).get();

    public BacterialAnalyzerTopBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos.below()) instanceof BacterialAnalyzerBlockEntity be) {
            player.openMenu(be, pos.below());
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_N;
            case EAST -> SHAPE_E;
            case SOUTH -> SHAPE_S;
            case WEST -> SHAPE_W;
            default -> super.getShape(state, level, pos, context);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);

        level.removeBlock(pos.below(), false);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.IGNORE;
    }
}
