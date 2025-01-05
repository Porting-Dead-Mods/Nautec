package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.utils.ranges.FloatRange;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public interface BacteriaStats<C extends CollapsedBacteriaStats> {
    Codec<BacteriaStats<?>> CODEC =
            NTRegistries.BACTERIA_STATS_SERIALIZER.byNameCodec().dispatch(BacteriaStats::getSerializer, BacteriaStatsSerializer::mapCodec);
    StreamCodec<RegistryFriendlyByteBuf, BacteriaStats<?>> STREAM_CODEC =
            ByteBufCodecs.registry(NTRegistries.BACTERIA_STATS_SERIALIZER_KEY).dispatch(BacteriaStats::getSerializer, BacteriaStatsSerializer::streamCodec);

    FloatRange growthRate();

    FloatRange mutationResistance();

    FloatRange productionRate();

    IntRange lifespan();

    C collapse();

    int color();

    BacteriaStatsSerializer<?, ?> getSerializer();
}
