package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.content.bacteria.CollapsedStats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public interface BacteriaStats {
    Codec<BacteriaStats> CODEC =
            NTRegistries.BACTERIA_STATS_SERIALIZER.byNameCodec().dispatch(BacteriaStats::getSerializer, BacteriaStatsSerializer::mapCodec);
    StreamCodec<RegistryFriendlyByteBuf, BacteriaStats> STREAM_CODEC =
            ByteBufCodecs.registry(NTRegistries.BACTERIA_STATS_SERIALIZER_KEY).dispatch(BacteriaStats::getSerializer, BacteriaStatsSerializer::streamCodec);


    List<Float> growthRate();

    List<Float> mutationResistance();

    List<Float> productionRate();

    List<Integer> lifespan();

    CollapsedStats collapse();

    int color();

    BacteriaStatsSerializer<?> getSerializer();
}
