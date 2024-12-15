package com.portingdeadmods.nautec.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

import java.util.Objects;
import java.util.function.Function;

public record ComponentBacteriaStorage(ResourceKey<Bacteria> bacteria, long bacteriaAmount) {
    public static final ComponentBacteriaStorage EMPTY = new ComponentBacteriaStorage(NTBacterias.EMPTY, 0);
    public static final Codec<ComponentBacteriaStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(NTRegistries.BACTERIA_KEY).fieldOf("bacteriaType").forGetter(ComponentBacteriaStorage::bacteria),
            Codec.LONG.fieldOf("bacteriaAmount").forGetter(ComponentBacteriaStorage::bacteriaAmount)
    ).apply(instance, ComponentBacteriaStorage::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentBacteriaStorage> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(NTRegistries.BACTERIA_KEY),
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
