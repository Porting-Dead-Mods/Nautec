package com.portingdeadmods.nautec.api.items;

import net.minecraft.world.item.ItemStack;

/**
 * Implement this interface on your item to auto register the capability for it
 */
public interface IPowerItem {
    default void onEnergyChanged(ItemStack itemStack) {
    }

    int getMaxInput();

    default int getMaxOutput() {
        return 100;
    }
}
