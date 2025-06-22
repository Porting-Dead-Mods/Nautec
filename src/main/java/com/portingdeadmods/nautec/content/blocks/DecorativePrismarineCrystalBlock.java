package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.content.blockentities.DecorativePrismarineCrystalBlockEntity;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DecorativePrismarineCrystalBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(4, 0, 4, 12, 16, 12),
        Block.box(2, 2, 2, 14, 14, 14)
    );

    public DecorativePrismarineCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DecorativePrismarineCrystalBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DecorativePrismarineCrystalBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        // Place the crystal at the bottom (pos) and build upward
        for (int i = 0; i < 6; i++) {
            BlockPos curPos = pos.above(i);
            if (i == 0) {
                // Bottom block stays as the main decorative crystal
                level.setBlockAndUpdate(curPos, NTBlocks.DECORATIVE_PRISMARINE_CRYSTAL.get().defaultBlockState());
            } else {
                // Other blocks become parts
                level.setBlockAndUpdate(curPos, NTBlocks.DECORATIVE_PRISMARINE_CRYSTAL_PART.get().defaultBlockState()
                        .setValue(DecorativePrismarineCrystalPartBlock.INDEX, i));
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        removeCrystal(level, player, pos);
        return true;
    }

    public static void removeCrystal(Level level, Player player, BlockPos thisPos) {
        if (thisPos != null) {
            // Remove 6 blocks starting from the bottom
            for (int i = 0; i < 6; i++) {
                BlockPos curPos = thisPos.above(i);
                level.removeBlock(curPos, false);
            }
        }
    }
}