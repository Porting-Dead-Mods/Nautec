package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.NTRegistries;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public interface Bacteria {
    Codec<Bacteria> CODEC = NTRegistries.BACTERIA_SERIALIZER.byNameCodec().dispatch(Bacteria::getSerializer, BacteriaSerializer::mapCodec);
    StreamCodec<RegistryFriendlyByteBuf, Bacteria> STREAM_CODEC = ByteBufCodecs.registry(NTRegistries.BACTERIA_SERIALIZER_KEY).dispatch(Bacteria::getSerializer, BacteriaSerializer::streamCodec);

    /*
    Bacteria! We begin with only one!
    Bacteria! Two's what we then become!
    Bacteria! Each of us becomes two more!
    Bacteria! We are stronger than before!
    Bacteria! We keep growing at this rate!
    Bacteria! No longer shall we wait!
    Bacteria! The plan now unfolds!
    Bacteria! We will take over the world!
     */
    ResourceLocation id();

    BacteriaStats stats();

    BacteriaSerializer<?> getSerializer();

    static Builder of() {
        return new Builder();
    }

    class Builder {
        private float growthRate;
        private float mutationResistance;
        private float productionRate;
        private int lifespan;
        private int color;

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

        public Builder lifespan(int lifespan) {
            this.lifespan = lifespan;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Bacteria build(ResourceLocation location) {
            return new BacteriaImpl(location, new BacteriaStats(Items.AIR, growthRate, mutationResistance, productionRate, lifespan, color));
        }
    }
}
