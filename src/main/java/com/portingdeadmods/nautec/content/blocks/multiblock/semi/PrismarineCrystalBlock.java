package com.portingdeadmods.nautec.content.blocks.multiblock.semi;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalPartBlockEntity;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrismarineCrystalBlock extends LaserBlock {
    public PrismarineCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.PRISMARINE_CRYSTAL.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PrismarineCrystalBlock::new);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockPos firstPos = pos.above(2);
        for (int i = 0; i < 6; i++) {
            BlockPos curPos = firstPos.below(i);
            if (i == 2) {
                level.setBlockAndUpdate(curPos, NTBlocks.PRISMARINE_CRYSTAL.get().defaultBlockState());
            } else {
                level.setBlockAndUpdate(curPos, NTBlocks.PRISMARINE_CRYSTAL_PART.get().defaultBlockState()
                        .setValue(PrismarineCrystalPartBlock.INDEX, i));
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        removeCrystal(level, pos);
        return true;
    }

    public static void removeCrystal(Level level, BlockPos thisPos) {
        if (thisPos != null) {
            BlockPos topPos = thisPos.above(2);
            for (int i = 0; i < 6; i++) {
                BlockPos curPos = topPos.below(i);
                level.removeBlock(curPos, false);
            }
        }
    }
}
