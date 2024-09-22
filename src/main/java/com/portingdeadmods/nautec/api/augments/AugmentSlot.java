package com.portingdeadmods.nautec.api.augments;

import com.portingdeadmods.nautec.MJRegistries;

public interface AugmentSlot {
    default String getName() {
        return MJRegistries.AUGMENT_SLOT.getKey(this).getPath();
    }
}
