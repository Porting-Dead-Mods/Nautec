package com.portingdeadmods.nautec.api.augments;

import com.portingdeadmods.nautec.MJRegistries;

public class AugmentType<T extends Augment> {
    private final AugmentConstructor<T> augmentConstructor;

    public static<T extends Augment> AugmentType<T> of(AugmentConstructor<T> constructor) {
        return new AugmentType<>(constructor);
    }

    private AugmentType(AugmentConstructor<T> augmentConstructor) {
        this.augmentConstructor = augmentConstructor;
    }

    public T create(AugmentSlot augmentSlot) {
        return augmentConstructor.create(augmentSlot);
    }

    @Override
    public String toString() {
        return MJRegistries.AUGMENT_TYPE.getKey(this).toString();
    }

    @FunctionalInterface
    public interface AugmentConstructor<T extends Augment> {
        T create(AugmentSlot augmentSlot);
    }
}
