package com.portingdeadmods.modjam.api.augments;

import com.portingdeadmods.modjam.MJRegistries;
import net.minecraft.resources.ResourceLocation;

public interface AugmentSlot {
    default String getName() {
        return MJRegistries.AUGMENT_SLOT.getKey(this).getPath();
    }
}
