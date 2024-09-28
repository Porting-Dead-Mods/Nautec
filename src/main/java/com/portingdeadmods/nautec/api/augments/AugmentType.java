package com.portingdeadmods.nautec.api.augments;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;

import java.util.List;

public class AugmentType<T extends Augment> {
    private final AugmentConstructor<T> augmentConstructor;
    private final List<AugmentSlot> augmentSlots;

    public static <T extends Augment> AugmentType<T> of(AugmentConstructor<T> constructor, AugmentSlot... compatibleSlots) {
        return new AugmentType<>(constructor, compatibleSlots);
    }

    private AugmentType(AugmentConstructor<T> augmentConstructor, AugmentSlot... compatibleSlots) {
        this.augmentConstructor = augmentConstructor;
        if (compatibleSlots.length == 0) {
            Nautec.LOGGER.warn("The augment: {} does not have any compatible slots, meaning it cannot be applied to the player.", NTRegistries.AUGMENT_TYPE.getKey(this));
        }
        this.augmentSlots = List.of(compatibleSlots);
    }

    public T create(AugmentSlot augmentSlot) {
        return augmentConstructor.create(augmentSlot);
    }

    public List<AugmentSlot> getAugmentSlots() {
        return augmentSlots;
    }

    @Override
    public String toString() {
        return NTRegistries.AUGMENT_TYPE.getKey(this).toString();
    }

    @FunctionalInterface
    public interface AugmentConstructor<T extends Augment> {
        T create(AugmentSlot augmentSlot);
    }
}
