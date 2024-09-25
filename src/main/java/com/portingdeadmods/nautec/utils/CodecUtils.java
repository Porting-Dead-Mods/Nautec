package com.portingdeadmods.nautec.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Set;

public final class CodecUtils {
    public static final Codec<Item> ITEM_CODEC = ResourceLocation.CODEC.xmap(BuiltInRegistries.ITEM::get, BuiltInRegistries.ITEM::getKey);
    public static final StreamCodec<ByteBuf, Item> ITEM_STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(BuiltInRegistries.ITEM::get, BuiltInRegistries.ITEM::getKey);

    public static <E> Codec<Set<E>> set(final Codec<E> elementCodec) {
        return set(elementCodec, 0, Integer.MAX_VALUE);
    }

    public static <E> Codec<Set<E>> set(final Codec<E> elementCodec, final int minSize, final int maxSize) {
        return new SetCodec<>(elementCodec, minSize, maxSize);
    }

}
