package com.portingdeadmods.nautec.content.blocks.multiblock.semi;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalBlockEntity;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PrismarineCrystalPartBlock extends LaserBlock {
    public static final IntegerProperty INDEX = IntegerProperty.create("index", 0, 5);
    public static final VoxelShape BOTTOM_SHAPE = Block.box(2, 4, 2, 14, 16, 14);
    public static final VoxelShape TOP_SHAPE = Block.box(2, 0, 2, 14, 12, 14);

    public PrismarineCrystalPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(INDEX, 0));
    }

    @Override
    public boolean waterloggable() {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(INDEX));
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
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.PRISMARINE_CRYSTAL_PART.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PrismarineCrystalPartBlock::new);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        for (int i = -2; i < 4; i++) {
            BlockPos blockPos = pos.above(i);
            if (level.getBlockEntity(blockPos) instanceof PrismarineCrystalBlockEntity){
                PrismarineCrystalBlock.removeCrystal(level, player, blockPos);
            }
        }
        return true;
    }
}
