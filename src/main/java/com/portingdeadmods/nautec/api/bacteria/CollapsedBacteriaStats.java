package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.NTRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public interface CollapsedBacteriaStats {
    Codec<CollapsedBacteriaStats> CODEC =
            NTRegistries.BACTERIA_STATS_SERIALIZER.byNameCodec().dispatch(CollapsedBacteriaStats::getSerializer, BacteriaStatsSerializer::collapsedMapCodec);
    StreamCodec<RegistryFriendlyByteBuf, CollapsedBacteriaStats> STREAM_CODEC =
            ByteBufCodecs.registry(NTRegistries.BACTERIA_STATS_SERIALIZER_KEY).dispatch(CollapsedBacteriaStats::getSerializer, BacteriaStatsSerializer::collapsedStreamCodec);

    BacteriaStatsSerializer<?, ?> getSerializer();
}
