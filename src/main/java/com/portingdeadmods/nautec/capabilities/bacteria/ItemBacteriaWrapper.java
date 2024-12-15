package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Supplier;

public record ItemBacteriaWrapper(Supplier<DataComponentType<Optional<ComponentBacteriaStorage>>> componentType, ItemStack itemStack) implements IBacteriaStorage {
    @Override
    public void setBacteria(Bacteria bacteria) {
        itemStack.set(componentType, Optional.of(new ComponentBacteriaStorage(bacteria, getBacteriaAmount())));
    }

    @Override
    public Bacteria getBacteria() {
        return itemStack.get(componentType).get().bacteria();
    }

    @Override
    public void setBacteriaAmount(long bacteriaAmount) {
        itemStack.set(componentType, Optional.of(new ComponentBacteriaStorage(getBacteria(), bacteriaAmount)));
    }

    @Override
    public long getBacteriaAmount() {
        return itemStack.get(componentType).get().bacteriaAmount();
    }
}
