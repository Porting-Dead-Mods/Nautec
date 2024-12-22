package com.portingdeadmods.nautec.data.components;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record ComponentBacteriaStorage(BacteriaInstance bacteriaInstance) {
    public static final ComponentBacteriaStorage EMPTY = new ComponentBacteriaStorage(BacteriaInstance.EMPTY);

    public static final Codec<ComponentBacteriaStorage> CODEC =
            BacteriaInstance.CODEC.xmap(ComponentBacteriaStorage::new, ComponentBacteriaStorage::bacteriaInstance);
    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentBacteriaStorage> STREAM_CODEC =
            BacteriaInstance.STREAM_CODEC.map(ComponentBacteriaStorage::new, ComponentBacteriaStorage::bacteriaInstance);

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ComponentBacteriaStorage(BacteriaInstance instance))) return false;
        return Objects.equals(bacteriaInstance, instance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bacteriaInstance);
    }
}
