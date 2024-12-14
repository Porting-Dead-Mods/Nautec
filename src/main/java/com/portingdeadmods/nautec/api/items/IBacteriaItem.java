package com.portingdeadmods.nautec.api.items;

import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import net.minecraft.world.item.ItemStack;

public interface IBacteriaItem {
    default IBacteriaStorage getStorage(ItemStack stack) {
        return stack.getCapability(NTCapabilities.BacteriaStorage.ITEM);
    }
}
