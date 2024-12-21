package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public record ItemBacteriaWrapper(Supplier<DataComponentType<ComponentBacteriaStorage>> componentType, ItemStack itemStack) implements IBacteriaStorage {
    @Override
    public void setBacteria(int slot, BacteriaInstance bacteriaInstance) {
        itemStack.set(componentType, new ComponentBacteriaStorage(bacteriaInstance));
    }

    @Override
    public BacteriaInstance getBacteria(int slot) {
        return itemStack.get(componentType).bacteriaInstance();
    }

    @Override
    public int getBacteriaSlots() {
        return 1;
    }
}
