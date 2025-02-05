package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.utils.RNGUtils;
import com.portingdeadmods.nautec.utils.ranges.FloatRange;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import com.portingdeadmods.nautec.utils.ranges.LongRange;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public record SimpleBacteria(LongRange initialSize, Resource.ItemResource resource, BacteriaStats<?> stats) implements Bacteria {
    public static Builder of() {
        return new Builder();
    }

    @Override
    public long rollSize() {
        return RNGUtils.uniformRandLong(initialSize.getMin(), initialSize.getMax());
    }

    @Override
    public BacteriaSerializer<SimpleBacteria> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static final class Serializer implements BacteriaSerializer<SimpleBacteria> {
        public static final Serializer INSTANCE = new Serializer();
        public static final MapCodec<SimpleBacteria> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                LongRange.MAP_CODEC.fieldOf("initial_size").forGetter(SimpleBacteria::initialSize),
                Resource.ItemResource.CODEC.fieldOf("bacteria").forGetter(SimpleBacteria::resource),
                BacteriaStats.CODEC.fieldOf("stats").forGetter(SimpleBacteria::stats)
        ).apply(instance, SimpleBacteria::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteria> STREAM_CODEC = StreamCodec.composite(
                LongRange.STREAM_CODEC,
                SimpleBacteria::initialSize,
                Resource.ItemResource.STREAM_CODEC,
                SimpleBacteria::resource,
                BacteriaStats.STREAM_CODEC,
                SimpleBacteria::stats,
                SimpleBacteria::new
        );

        private Serializer() {
        }

        @Override
        public MapCodec<SimpleBacteria> mapCodec() {
            return MAP_CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SimpleBacteria> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements Bacteria.Builder<SimpleBacteria> {
        private LongRange initialSize = LongRange.of(0, 0);
        private Resource.ItemResource resource = new Resource.ItemResource(Items.AIR);
        private FloatRange growthRate = FloatRange.of(0F, 0F);
        private FloatRange mutationResistance = FloatRange.of(0F, 0F);
        private FloatRange productionRate = FloatRange.of(0F, 0F);
        private IntRange lifespan = IntRange.of(0, 0);
        private int color;

        public Builder initialSize(LongRange initialSize) {
            this.initialSize = initialSize;
            return this;
        }

        public Builder resource(Item resource) {
            this.resource = new Resource.ItemResource(resource);
            return this;
        }

        public Builder growthRate(FloatRange growthRate) {
            this.growthRate = growthRate;
            return this;
        }

        public Builder mutationResistance(FloatRange mutationResistance) {
            this.mutationResistance = mutationResistance;
            return this;
        }

        public Builder productionRate(FloatRange productionRate) {
            this.productionRate = productionRate;
            return this;
        }

        public Builder lifespan(IntRange lifespan) {
            this.lifespan = lifespan;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public SimpleBacteria build() {
            return new SimpleBacteria(initialSize, resource, new SimpleBacteriaStats(growthRate, mutationResistance, productionRate, lifespan, color));
        }
    }
}
