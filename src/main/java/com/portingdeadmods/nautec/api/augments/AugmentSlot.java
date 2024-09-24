package com.portingdeadmods.nautec.api.augments;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.client.renderer.augments.ModelPartGetter;

public interface AugmentSlot {
    ModelPartGetter getModelPart();

    default String getName() {
        return NTRegistries.AUGMENT_SLOT.getKey(this).getPath();
    }
}
