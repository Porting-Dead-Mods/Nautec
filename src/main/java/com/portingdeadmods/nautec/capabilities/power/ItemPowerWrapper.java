package com.portingdeadmods.nautec.capabilities.power;

import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Range;

public record ItemPowerWrapper(ItemStack itemStack, IPowerItem powerItem) implements IPowerStorage {
    public ItemPowerWrapper {
        if (!itemStack.has(NTDataComponents.POWER)) {
            throw new IllegalStateException("The item: " + itemStack.getItem() + " is missing the Power Datacomponent");
        }
    }

    private ComponentPowerStorage getComponent() {
        return itemStack.getOrDefault(NTDataComponents.POWER, ComponentPowerStorage.EMPTY);
    }

    private void setComponent(ComponentPowerStorage powerStorage) {
        this.itemStack.set(NTDataComponents.POWER, powerStorage);
    }

    @Override
    public void onEnergyChanged() {
        IPowerStorage.super.onEnergyChanged();
        powerItem.onEnergyChanged(itemStack);
    }

    @Override
    public int getPowerStored() {
        return getComponent().powerStored();
    }

    @Override
    public int getPowerCapacity() {
        return getComponent().powerCapacity();
    }

    @Override
    public @Range(from = 0, to = 1) float getPurity() {
        return getComponent().purity();
    }

    @Override
    public void setPowerStored(int powerStored) {
        if (getPowerStored() != powerStored) {
            onEnergyChanged();
            setComponent(new ComponentPowerStorage(powerStored, getComponent().powerCapacity(), getComponent().purity()));
        }
    }

    @Override
    public void setPowerCapacity(int powerCapacity) {
        setComponent(new ComponentPowerStorage(getComponent().powerStored(), powerCapacity, getComponent().purity()));
    }

    @Override
    public void setPurity(float purity) {
        setComponent(new ComponentPowerStorage(getComponent().powerStored(), getComponent().powerCapacity(), purity));
    }

    @Override
    public int getMaxInput() {
        return powerItem.getMaxInput();
    }

    @Override
    public int getMaxOutput() {
        return powerItem.getMaxOutput();
    }
}
