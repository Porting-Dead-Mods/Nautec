package com.portingdeadmods.nautec.api.augments;

import com.portingdeadmods.nautec.NTRegistries;

public interface AugmentSlot {
    default String getName() {
        return NTRegistries.AUGMENT_SLOT.getKey(this).getPath();
    }
}
