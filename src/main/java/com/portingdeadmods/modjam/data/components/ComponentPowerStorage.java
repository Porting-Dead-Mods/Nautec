package com.portingdeadmods.modjam.data.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record ComponentPowerStorage(int powerStored, int powerCapacity, float purity) {
    public static final ComponentPowerStorage EMPTY = new ComponentPowerStorage(0, 0, 0);

    public static final Codec<ComponentPowerStorage> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("power_stored").forGetter(ComponentPowerStorage::powerStored),
            Codec.INT.fieldOf("power_capacity").forGetter(ComponentPowerStorage::powerCapacity),
            Codec.FLOAT.fieldOf("purity").forGetter(ComponentPowerStorage::purity)
    ).apply(builder, ComponentPowerStorage::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ComponentPowerStorage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ComponentPowerStorage::powerStored,
            ByteBufCodecs.INT,
            ComponentPowerStorage::powerStored,
            ByteBufCodecs.FLOAT,
            ComponentPowerStorage::purity,
            ComponentPowerStorage::new
    );

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponentPowerStorage that)) return false;
        return powerStored == that.powerStored && powerCapacity == that.powerCapacity && purity == that.purity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(powerStored, powerCapacity, purity);
    }
}
