package com.portingdeadmods.nautec.mixin;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.utils.NTProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(KelpBlock.class)
public abstract class KelpBlockMixin extends GrowingPlantHeadBlock {

    protected KelpBlockMixin(Properties properties, Direction growthDirection, VoxelShape shape, boolean scheduleFluidTicks, double growPerTickProbability) {
        super(properties, growthDirection, shape, scheduleFluidTicks, growPerTickProbability);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NTProperties.KELP_AGE);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(LevelAccessor level) {
        return this.defaultBlockState().setValue(NTProperties.KELP_AGE, level.getRandom().nextInt(NTConfig.kelpHeight + 1));
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(NTProperties.KELP_AGE) < NTConfig.kelpHeight;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(NTProperties.KELP_AGE) < NTConfig.kelpHeight &&
                net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos.relative(this.growthDirection), state, random.nextDouble() < 0.14)) {
            BlockPos blockpos = pos.relative(this.growthDirection);
            if (this.canGrowInto(level.getBlockState(blockpos))) {
                BlockState growState = getGrowIntoState(state, random);
                level.setBlockAndUpdate(blockpos, growState);
                net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, blockpos, level.getBlockState(blockpos));
            }
        }
    }

    @Override
    protected @NotNull BlockState getGrowIntoState(BlockState state, RandomSource random) {
        int currentAge = state.getValue(NTProperties.KELP_AGE);
        return state.setValue(NTProperties.KELP_AGE, Math.min(currentAge + 1, NTConfig.kelpHeight));
    }

    @Override
    public @NotNull BlockState getMaxAgeState(BlockState state) {
        return state.setValue(NTProperties.KELP_AGE, NTConfig.kelpHeight);
    }

    @Override
    public boolean isMaxAge(BlockState state) {
        return state.getValue(NTProperties.KELP_AGE) == NTConfig.kelpHeight;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.relative(this.growthDirection);
        int i = Math.min(state.getValue(NTProperties.KELP_AGE) + 1, NTConfig.kelpHeight);
        int j = this.getBlocksToGrowWhenBonemealed(random);

        for (int k = 0; k < j && this.canGrowInto(level.getBlockState(blockpos)); k++) {
            level.setBlockAndUpdate(blockpos, state.setValue(NTProperties.KELP_AGE, i));
            blockpos = blockpos.relative(this.growthDirection);
            i = Math.min(i + 1, NTConfig.kelpHeight);
        }
    }

}