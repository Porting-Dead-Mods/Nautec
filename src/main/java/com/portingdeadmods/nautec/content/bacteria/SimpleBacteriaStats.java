package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStatsSerializer;
import com.portingdeadmods.nautec.utils.ranges.FloatRange;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SimpleBacteriaStats(FloatRange growthRate,
                                  FloatRange mutationResistance,
                                  FloatRange productionRate,
                                  IntRange lifespan,
                                  int color
) implements BacteriaStats<SimpleCollapsedStats> {
    public static final SimpleBacteriaStats EMPTY = new SimpleBacteriaStats(
            FloatRange.of(0F, 0F),
            FloatRange.of(0F, 0F),
            FloatRange.of(0F, 0F),
            IntRange.of(1200, 2400),
            -1
    );
    public static final MapCodec<SimpleBacteriaStats> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    FloatRange.MAP_CODEC.fieldOf("growth_rate").forGetter(SimpleBacteriaStats::growthRate),
                    FloatRange.MAP_CODEC.fieldOf("mutation_resistance").forGetter(SimpleBacteriaStats::mutationResistance),
                    FloatRange.MAP_CODEC.fieldOf("production_rate").forGetter(SimpleBacteriaStats::productionRate),
                    IntRange.MAP_CODEC.fieldOf("lifespan").forGetter(SimpleBacteriaStats::lifespan),
                    Codec.INT.fieldOf("color").forGetter(SimpleBacteriaStats::color)
            ).apply(instance, SimpleBacteriaStats::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteriaStats> STREAM_CODEC = StreamCodec.composite(
            FloatRange.STREAM_CODEC,
            SimpleBacteriaStats::growthRate,
            FloatRange.STREAM_CODEC,
            SimpleBacteriaStats::mutationResistance,
            FloatRange.STREAM_CODEC,
            SimpleBacteriaStats::productionRate,
            IntRange.STREAM_CODEC,
            SimpleBacteriaStats::lifespan,
            ByteBufCodecs.INT,
            SimpleBacteriaStats::color,
            SimpleBacteriaStats::new
    );

    @Override
    public SimpleCollapsedStats collapse() {
        return SimpleCollapsedStats.collapse(this);
    }

    @Override
    public SimpleCollapsedStats collapseMaxStats() {
        return SimpleCollapsedStats.getMaxStats(this);
    }

    @Override
    public BacteriaStatsSerializer<SimpleCollapsedStats, SimpleBacteriaStats> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static final class Serializer implements BacteriaStatsSerializer<SimpleCollapsedStats, SimpleBacteriaStats> {
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

        @Override
        public MapCodec<SimpleCollapsedStats> collapsedMapCodec() {
            return SimpleCollapsedStats.CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SimpleCollapsedStats> collapsedStreamCodec() {
            return SimpleCollapsedStats.STREAM_CODEC;
        }
    }

}
