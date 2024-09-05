package com.portingdeadmods.modjam.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GrowingPlantHeadBlock.class)
public abstract class GrowingPlantHeadBlockMixin {
    @Shadow
    protected abstract boolean isMaxAge(BlockState state);

    @Shadow
    protected abstract BlockState getMaxAgeState(BlockState state);

    @Shadow
    protected abstract int getBlocksToGrowWhenBonemealed(RandomSource random);

    @Shadow
    protected abstract boolean canGrowInto(BlockState state);

    @Shadow
    protected abstract BlockState getGrowIntoState(BlockState state, RandomSource random);

    @Shadow @Final private double growPerTickProbability;

    /**
     * @author Leclowndu93150
     * @reason because
     */
    @Overwrite
    protected boolean isRandomlyTicking(BlockState state) {
        if (state.getBlock() instanceof KelpBlock) {
            return state.getValue(GrowingPlantHeadBlock.AGE) < 40;
        }
        return state.getValue(GrowingPlantHeadBlock.AGE) < GrowingPlantHeadBlock.MAX_AGE; // Default behavior for other plants
    }

    /**
     * @author Leclowndu93150
     * @reason maybe
     */
    @Overwrite
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getBlock() instanceof KelpBlock) {
            if (state.getValue(GrowingPlantHeadBlock.AGE) < 40 && net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos.relative(Direction.UP), state, random.nextDouble() < this.growPerTickProbability)) {
                BlockPos blockpos = pos.relative(Direction.UP);
                if (this.canGrowInto(level.getBlockState(blockpos))) {
                    level.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, level.random));
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, blockpos, level.getBlockState(blockpos));
                }
            }
        } else {
            // Original behavior for other blocks
            if (state.getValue(GrowingPlantHeadBlock.AGE) < 25 && net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos.relative(Direction.UP), state, random.nextDouble() < this.growPerTickProbability)) {
                BlockPos blockpos = pos.relative(Direction.UP);
                if (this.canGrowInto(level.getBlockState(blockpos))) {
                    level.setBlockAndUpdate(blockpos, this.getGrowIntoState(state, level.random));
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, blockpos, level.getBlockState(blockpos));
                }
            }
        }
    }
}