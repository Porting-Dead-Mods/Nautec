package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.bacteria.Bacteria;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public record ItemBacteriaWrapper(Supplier<DataComponentType<ComponentBacteriaStorage>> componentType, ItemStack itemStack) implements IBacteriaStorage {
    @Override
    public void setBacteria(Bacteria bacteria) {
        itemStack.set(componentType, new ComponentBacteriaStorage(bacteria, getBacteriaAmount()));
    }

    @Override
    public Bacteria getBacteria() {
        return itemStack.get(componentType).bacteria();
    }

    @Override
    public void setBacteriaAmount(long bacteriaAmount) {
        itemStack.set(componentType, new ComponentBacteriaStorage(getBacteria(), bacteriaAmount));
    }

    @Override
    public long getBacteriaAmount() {
        return itemStack.get(componentType).bacteriaAmount();
    }
}
