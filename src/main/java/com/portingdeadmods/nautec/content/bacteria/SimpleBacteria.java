package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public record SimpleBacteria(Resource.ItemResource resource, BacteriaStats initialStats) implements Bacteria {
    public static Builder of() {
        return new Builder();
    }

    @Override
    public BacteriaSerializer<SimpleBacteria> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static final class Serializer implements BacteriaSerializer<SimpleBacteria> {
        public static final Serializer INSTANCE = new Serializer();
        public static final MapCodec<SimpleBacteria> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Resource.ItemResource.CODEC.fieldOf("resource").forGetter(SimpleBacteria::resource),
                BacteriaStats.CODEC.fieldOf("stats").forGetter(SimpleBacteria::initialStats)
        ).apply(instance, SimpleBacteria::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteria> STREAM_CODEC = StreamCodec.composite(
                Resource.ItemResource.STREAM_CODEC,
                SimpleBacteria::resource,
                BacteriaStats.STREAM_CODEC,
                SimpleBacteria::initialStats,
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
        private Resource.ItemResource resource = new Resource.ItemResource(Items.AIR);
        private List<Float> growthRate = List.of(0F, 0F);
        private List<Float> mutationResistance = List.of(0F, 0F);
        private List<Float> productionRate = List.of(0F, 0F);
        private List<Integer> lifespan = List.of(0, 0);
        private int color;

        public Builder resource(Item resource) {
            this.resource = new Resource.ItemResource(resource);
            return this;
        }

        public Builder growthRate(List<Float> growthRate) {
            if (growthRate.size() != 2) {
                throw new IllegalArgumentException("GrowthRate builder must get exactly 2 values");
            }

            this.growthRate = growthRate;
            return this;
        }

        public Builder mutationResistance(List<Float> mutationResistance) {
            if (mutationResistance.size() != 2) {
                throw new IllegalArgumentException("Mutation Resistance builder must get exactly 2 values");
            }

            this.mutationResistance = mutationResistance;
            return this;
        }

        public Builder productionRate(List<Float> productionRate) {
            if (productionRate.size() != 2) {
                throw new IllegalArgumentException("Production Rate builder must get exactly 2 values");
            }

            this.productionRate = productionRate;
            return this;
        }

        public Builder lifespan(List<Integer> lifespan) {
            if (lifespan.size() != 2) {
                throw new IllegalArgumentException("Lifespan builder must get exactly 2 values");
            }

            this.lifespan = lifespan;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public SimpleBacteria build() {
            return new SimpleBacteria(resource, new SimpleBacteriaStats(growthRate, mutationResistance, productionRate, lifespan, color));
        }
    }
}
