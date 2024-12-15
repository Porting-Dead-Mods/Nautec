package com.portingdeadmods.nautec.capabilities.bacteria;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Supplier;

public record ItemBacteriaWrapper(Supplier<DataComponentType<ComponentBacteriaStorage>> componentType, ItemStack itemStack) implements IBacteriaStorage {
    @Override
    public void setBacteria(ResourceKey<Bacteria> bacteria) {
        itemStack.set(componentType, new ComponentBacteriaStorage(bacteria, getBacteriaAmount()));
    }

    @Override
    public ResourceKey<Bacteria> getBacteria() {
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
