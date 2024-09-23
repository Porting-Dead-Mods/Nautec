package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.registries.NTAugments;
import org.jetbrains.annotations.Nullable;

public class UnderwaterMiningSpeed extends Augment {
    public UnderwaterMiningSpeed(AugmentSlot augmentSlot) {
        super(NTAugments.UNDERWATER_MINING_SPEED_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }
}
