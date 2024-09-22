package com.portingdeadmods.nautec.utils;

import com.mojang.serialization.Codec;

import java.util.Set;

public final class CodecUtils {
    public static <E> Codec<Set<E>> set(final Codec<E> elementCodec) {
        return set(elementCodec, 0, Integer.MAX_VALUE);
    }

    public static <E> Codec<Set<E>> set(final Codec<E> elementCodec, final int minSize, final int maxSize) {
        return new SetCodec<>(elementCodec, minSize, maxSize);
    }

}
