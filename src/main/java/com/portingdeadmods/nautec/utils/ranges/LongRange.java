package com.portingdeadmods.nautec.utils.ranges;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Collection;
import java.util.Collections;

public class LongRange extends AbstractRange<Long>{
    public static final MapCodec<LongRange> MAP_CODEC = rangeMapCodec(Codec.LONG, LongRange::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, LongRange> STREAM_CODEC = rangeStreamCodec(ByteBufCodecs.VAR_LONG, LongRange::new);

    protected LongRange(long min, long max) {
        super(min, max);
    }

    @Override
    protected Collection<Long> collectPossibleValues() {
        return Collections.emptyList();
    }

    public static LongRange of(long min, long max) {
        return new LongRange(min, max);
    }
}
