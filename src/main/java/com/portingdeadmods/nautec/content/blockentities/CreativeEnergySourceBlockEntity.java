package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CreativeEnergySourceBlockEntity extends BlockEntity implements IEnergyStorage {
    private static final int MAX_ENERGY = Integer.MAX_VALUE;

    public CreativeEnergySourceBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.CREATIVE_ENERGY_SOURCE.get(), pos, blockState);
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return toExtract;
    }

    @Override
    public int getEnergyStored() {
        return MAX_ENERGY;
    }

    @Override
    public int getMaxEnergyStored() {
        return MAX_ENERGY;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
