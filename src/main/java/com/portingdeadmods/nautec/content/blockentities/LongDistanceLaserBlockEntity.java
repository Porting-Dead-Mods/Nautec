package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blocks.LongDistanceLaserBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class LongDistanceLaserBlockEntity extends LaserBlockEntity {
    public LongDistanceLaserBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.LONG_DISTANCE_LASER.get(), blockPos, blockState);
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of(getBlockState().getValue(LongDistanceLaserBlock.FACING).getOpposite());
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        return ObjectSet.of(getBlockState().getValue(LongDistanceLaserBlock.FACING));
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public int getMaxLaserDistance() {
        return NTConfig.longDistanceLaserDistance;
    }

    @Override
    public void commonTick() {
        super.commonTick();

        transmitPower(this.power);
    }
}
