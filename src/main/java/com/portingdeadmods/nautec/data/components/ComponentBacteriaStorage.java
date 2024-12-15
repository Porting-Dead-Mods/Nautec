package com.portingdeadmods.nautec.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.bacteria.Bacteria;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Objects;

public record ComponentBacteriaStorage(Bacteria bacteria, long bacteriaAmount) {
    public static final ComponentBacteriaStorage EMPTY = new ComponentBacteriaStorage(new Bacteria() {
        @Override
        public ResourceLocation id() {
            return Nautec.rl("empty");
        }

        @Override
        public Item type() {
            return Items.AIR;
        }

        @Override
        public float growthRate() {
            return 0;
        }

        @Override
        public float mutationResistance() {
            return 0;
        }

        @Override
        public float productionRate() {
            return 0;
        }

        @Override
        public int lifespan() {
            return 0;
        }

        @Override
        public int color() {
            return 0;
        }
    }, 0);
    public static final Codec<ComponentBacteriaStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Bacteria.CODEC.fieldOf("bacteriaType").forGetter(ComponentBacteriaStorage::bacteria),
            Codec.LONG.fieldOf("bacteriaAmount").forGetter(ComponentBacteriaStorage::bacteriaAmount)
    ).apply(instance, ComponentBacteriaStorage::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentBacteriaStorage> STREAM_CODEC = StreamCodec.composite(
            Bacteria.STREAM_CODEC,
            ComponentBacteriaStorage::bacteria,
            ByteBufCodecs.VAR_LONG,
            ComponentBacteriaStorage::bacteriaAmount,
            ComponentBacteriaStorage::new
    );

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ComponentBacteriaStorage(Bacteria bacteria1, long amount))) return false;
        return bacteriaAmount == amount && Objects.equals(bacteria, bacteria1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bacteria, bacteriaAmount);
    }
}
