package com.portingdeadmods.modjam.capabilities.power;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;

public class PowerStorage implements IPowerStorage, INBTSerializable<CompoundTag> {
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
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("power_stored", this.powerStored);
        tag.putInt("power_capacity", this.powerCapacity);
        tag.putFloat("purity", this.purity);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.powerStored = nbt.getInt("power_stored");
        this.powerCapacity = nbt.getInt("power_capacity");
        this.purity = nbt.getFloat("purity");
    }
}
