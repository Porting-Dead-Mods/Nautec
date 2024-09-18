package com.portingdeadmods.modjam.api.augments;

import net.minecraft.resources.ResourceLocation;

public interface AugmentSlot {
    int getSlotId();

    ResourceLocation getLocation();

    default String getName() {
        return getLocation().getPath();
    }
}
