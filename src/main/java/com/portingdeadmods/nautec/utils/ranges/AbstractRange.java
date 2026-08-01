package com.portingdeadmods.nautec.utils.ranges;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;

public abstract class AbstractRange<T extends Number> {
    private final T min;
    private final T max;
    private final Collection<T> possibleValues;

    protected AbstractRange(T minInclusive, T maxInclusive) {
        this.min = minInclusive;
        this.max = maxInclusive;
        this.possibleValues = collectPossibleValues();
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public Collection<T> getPossibleValues() {
        return possibleValues;
    }

    protected abstract Collection<T> collectPossibleValues();

    @Override
    public String toString() {
        return min + " - " + max;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractRange<?> other = (AbstractRange<?>) obj;
        return Objects.equals(min, other.min) && Objects.equals(max, other.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), min, max);
    }

    public static <T extends Number, SELF extends AbstractRange<T>> MapCodec<SELF> rangeMapCodec(Codec<T> codec, BiFunction<T, T, SELF> constructor) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                codec.fieldOf("min").forGetter(SELF::getMin),
                codec.fieldOf("max").forGetter(SELF::getMax)
        ).apply(instance, constructor));
    }

    public static <T extends Number, SELF extends AbstractRange<T>>StreamCodec<RegistryFriendlyByteBuf, SELF> rangeStreamCodec(StreamCodec<ByteBuf, T> streamCodec, BiFunction<T, T, SELF> constructor) {
        return StreamCodec.composite(
                streamCodec,
                SELF::getMin,
                streamCodec,
                SELF::getMax,
                constructor
        );
    }
}
