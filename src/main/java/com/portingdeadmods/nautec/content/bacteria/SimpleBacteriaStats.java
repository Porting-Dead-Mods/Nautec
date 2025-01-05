package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStatsSerializer;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import com.portingdeadmods.nautec.utils.ranges.FloatRange;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record SimpleBacteriaStats(FloatRange growthRate,
                                  FloatRange mutationResistance,
                                  FloatRange productionRate,
                                  IntRange lifespan,
                                  int color) implements BacteriaStats<SimpleCollapsedStats> {
    public static final SimpleBacteriaStats EMPTY = new SimpleBacteriaStats(
            FloatRange.of(0F, 0F),
            FloatRange.of(0F, 0F),
            FloatRange.of(0F, 0F),
            IntRange.of(1200, 2400),
            -1
    );
    public static final MapCodec<SimpleBacteriaStats> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.list(Codec.FLOAT).fieldOf("growth_rate").forGetter(SimpleBacteriaStats::growthRate),
                    Codec.list(Codec.FLOAT).fieldOf("mutation_resistance").forGetter(SimpleBacteriaStats::mutationResistance),
                    Codec.list(Codec.FLOAT).fieldOf("production_rate").forGetter(SimpleBacteriaStats::productionRate),
                    Codec.list(Codec.INT).fieldOf("lifespan").forGetter(SimpleBacteriaStats::lifespan),
                    Codec.INT.fieldOf("color").forGetter(SimpleBacteriaStats::color)
            ).apply(instance, SimpleBacteriaStats::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteriaStats> STREAM_CODEC = StreamCodec.composite(
            CodecUtils.FLOAT_LIST_STREAM_CODEC,
            SimpleBacteriaStats::growthRate,
            CodecUtils.FLOAT_LIST_STREAM_CODEC,
            SimpleBacteriaStats::mutationResistance,
            CodecUtils.FLOAT_LIST_STREAM_CODEC,
            SimpleBacteriaStats::productionRate,
            CodecUtils.INT_LIST_STREAM_CODEC,
            SimpleBacteriaStats::lifespan,
            ByteBufCodecs.INT,
            SimpleBacteriaStats::color,
            SimpleBacteriaStats::new
    );

    @Override
    public SimpleCollapsedStats collapse() {
        return SimpleCollapsedStats.from(this);
    }

    @Override
    public BacteriaStatsSerializer<SimpleBacteriaStats> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static final class Serializer implements BacteriaStatsSerializer<SimpleBacteriaStats> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        @Override
        public MapCodec<SimpleBacteriaStats> mapCodec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SimpleBacteriaStats> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
