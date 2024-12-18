package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public record SimpleBacteria(BacteriaStats stats) implements Bacteria {
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
                BacteriaStats.CODEC.fieldOf("stats").forGetter(SimpleBacteria::stats)
        ).apply(instance, SimpleBacteria::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteria> STREAM_CODEC = StreamCodec.composite(
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

    public static class Builder {
        private Item resource = Items.AIR;
        private float growthRate;
        private float mutationResistance;
        private float productionRate;
        private float colonySize;
        private int lifespan;
        private int color;

        public Builder resource(Item resource) {
            this.resource = resource;
            return this;
        }

        public Builder growthRate(float growthRate) {
            this.growthRate = growthRate;
            return this;
        }

        public Builder mutationResistance(float mutationResistance) {
            this.mutationResistance = mutationResistance;
            return this;
        }

        public Builder productionRate(float productionRate) {
            this.productionRate = productionRate;
            return this;
        }

        public Builder colonySize(float colonySize) {
            this.colonySize = colonySize;
            return this;
        }

        public Builder lifespan(int lifespan) {
            this.lifespan = lifespan;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Bacteria build(ResourceLocation location) {
            return new SimpleBacteria(new BacteriaStats(resource, growthRate, mutationResistance, productionRate, colonySize, lifespan, color));
        }
    }
}
