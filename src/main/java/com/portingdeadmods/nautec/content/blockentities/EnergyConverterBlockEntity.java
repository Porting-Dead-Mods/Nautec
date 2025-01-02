package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class EnergyConverterBlockEntity extends LaserBlockEntity implements IEnergyStorage {
    private static final int FE_CONVERSION_RATE = 100;
    private int feStored = 0;
    private static final int MAX_FE = 100000;

    public EnergyConverterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.ENERGY_CONVERTER.get(), blockPos, blockState);
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        return ObjectSet.of(Direction.values());
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public void commonTick() {
        if (!level.isClientSide) {
            Direction[] directions = Direction.values();
            for (Direction direction : directions) {
                BlockPos pos = worldPosition.relative(direction);
                if (level.getBlockEntity(pos) instanceof LaserBlockEntity laserBlockEntity) {
                    if (laserBlockEntity.getLaserInputs().contains(direction.getOpposite())) {
                        int energyToConvert = Math.min(FE_CONVERSION_RATE, feStored);
                        if (energyToConvert > 0) {
                            transmitPower(energyToConvert);
                            feStored -= energyToConvert;
                        }
                    }
                }
            }
        }
    }
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(MAX_FE - feStored, maxReceive);
        if (!simulate) {
            feStored += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return feStored;
    }

    @Override
    public int getMaxEnergyStored() {
        return MAX_FE;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
