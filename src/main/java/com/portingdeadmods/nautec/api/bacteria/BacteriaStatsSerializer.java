package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface BacteriaStatsSerializer<C extends CollapsedBacteriaStats, T extends BacteriaStats<C>> {
    MapCodec<T> mapCodec();

    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();

    MapCodec<C> collapsedMapCodec();

    StreamCodec<RegistryFriendlyByteBuf, C> collapsedStreamCodec();
}