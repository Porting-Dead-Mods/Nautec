package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record SimpleBacteria(ResourceLocation id, BacteriaStats stats) implements Bacteria {
    @Override
    public BacteriaSerializer<SimpleBacteria> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static final class Serializer implements BacteriaSerializer<SimpleBacteria> {
        public static final Serializer INSTANCE = new Serializer();
        public static final MapCodec<SimpleBacteria> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(SimpleBacteria::id),
                BacteriaStats.CODEC.fieldOf("stats").forGetter(SimpleBacteria::stats)
        ).apply(instance, SimpleBacteria::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteria> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC,
                SimpleBacteria::id,
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
}
