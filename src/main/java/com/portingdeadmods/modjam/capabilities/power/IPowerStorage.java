package com.portingdeadmods.modjam.capabilities.power;

import net.minecraft.util.Mth;
import org.jetbrains.annotations.Range;

public interface IPowerStorage {
    int getPowerStored();

    int getPowerCapacity();

    @Range(from = 0, to = 1)
    float getPurity();

    void setPowerStored(int powerStored);

    void setPowerCapacity(int powerCapacity);

    void setPurity(float purity);

    int getMaxInput();

    int getMaxOutput();

    default void onEnergyChanged() {
    }

    default boolean canFillPower() {
        return getMaxInput() > 0;
    }

    default boolean canDrainPower() {
        return getMaxOutput() > 0;
    }

    /**
     * Removes power from the storage. Returns the amount of power that was removed.
     *
     * @param value    The amount of power being drained.
     * @param simulate whether the draining should only be simulated
     * @return Amount of power that was extracted from the storage.
     */
    default int tryDrainPower(int value, boolean simulate) {
        if (!canDrainPower() || value <= 0) {
            return 0;
        }

        int powerExtracted = Math.min(getPowerStored(), Math.min(getMaxOutput(), value));
        if (!simulate) {
            setPowerStored(getPowerStored() - powerExtracted);
        }
        return powerExtracted;
    }

    /**
     * Adds power to the storage. Returns the amount of power that was accepted.
     *
     * @param value    The amount of power being filled.
     * @param simulate whether the filling should only be simulated
     * @return Amount of power that was accepted by the storage.
     */
    default int tryFillPower(int value, boolean simulate) {
        if (!canFillPower() || value <= 0) {
            return 0;
        }

        int powerReceived = Mth.clamp(getPowerCapacity() - getPowerStored(), 0, Math.min(getMaxInput(), value));
        if (!simulate) {
            setPowerStored(getPowerStored() + powerReceived);
        }
        return powerReceived;
    }
}
