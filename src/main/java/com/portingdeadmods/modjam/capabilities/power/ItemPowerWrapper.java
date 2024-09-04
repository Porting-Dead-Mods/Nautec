package com.portingdeadmods.modjam.capabilities.power;

import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Range;

public record ItemPowerWrapper(ItemStack itemStack) implements IPowerStorage {
    public ItemPowerWrapper {
        if (!itemStack.has(MJDataComponents.POWER)) {
            throw new IllegalStateException("The item: " + itemStack);
        }
    }

    private ComponentPowerStorage getComponent() {
        return itemStack.getOrDefault(MJDataComponents.POWER, ComponentPowerStorage.EMPTY);
    }

    @Override
    public int getPowerStored() {
        return 0;
    }

    @Override
    public int getPowerCapacity() {
        return 0;
    }

    @Override
    public @Range(from = 0, to = 1) float getPurity() {
        return 0;
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
}
