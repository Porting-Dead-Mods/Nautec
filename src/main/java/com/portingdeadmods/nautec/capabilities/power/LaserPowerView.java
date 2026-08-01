package com.portingdeadmods.nautec.capabilities.power;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import org.jetbrains.annotations.Range;

public record LaserPowerView(LaserBlockEntity blockEntity) implements IPowerStorage {
    @Override
    public int getPowerStored() {
        return blockEntity.getPower();
    }

    @Override
    public int getPowerCapacity() {
        return blockEntity.getPower();
    }

    @Override
    public @Range(from = 0, to = 1) float getPurity() {
        return blockEntity.getPurity();
    }

    @Override
    public void setPowerStored(int powerStored) {
    }

    @Override
    public void setPowerCapacity(int powerCapacity) {
    }

    @Override
    public void setPurity(float purity) {
    }

    @Override
    public int getMaxInput() {
        return 0;
    }

    @Override
    public int getMaxOutput() {
        return 0;
    }
}
