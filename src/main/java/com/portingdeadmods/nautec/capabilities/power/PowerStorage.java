package com.portingdeadmods.nautec.capabilities.power;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import org.jetbrains.annotations.Range;

public class PowerStorage implements IPowerStorage, ValueIOSerializable {
    private int powerStored;
    private int powerCapacity;
    private float purity;

    @Override
    public int getPowerStored() {
        return this.powerStored;
    }

    @Override
    public int getPowerCapacity() {
        return this.powerCapacity;
    }

    @Override
    public @Range(from = 0, to = 1) float getPurity() {
        return this.purity;
    }

    @Override
    public void setPowerStored(int powerStored) {
        this.powerStored = powerStored;
    }

    @Override
    public void setPowerCapacity(int powerCapacity) {
        this.powerCapacity = powerCapacity;
    }

    @Override
    public void setPurity(float purity) {
        this.purity = purity;
    }

    @Override
    public int getMaxInput() {
        return 0;
    }

    @Override
    public int getMaxOutput() {
        return 0;
    }

    @Override
    public void serialize(ValueOutput out) {
        out.putInt("power_stored", this.powerStored);
        out.putInt("power_capacity", this.powerCapacity);
        out.putFloat("purity", this.purity);
    }

    @Override
    public void deserialize(ValueInput in) {
        this.powerStored = in.getIntOr("power_stored", 0);
        this.powerCapacity = in.getIntOr("power_capacity", 0);
        this.purity = in.getFloatOr("purity", 0F);
    }
}
