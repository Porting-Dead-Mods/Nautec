package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.utils.ranges.FloatRange;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public interface CollapsedBacteriaStats {
    Codec<CollapsedBacteriaStats> CODEC =
            NTRegistries.BACTERIA_STATS_SERIALIZER.byNameCodec().dispatch(CollapsedBacteriaStats::getSerializer, BacteriaStatsSerializer::collapsedMapCodec);
    StreamCodec<RegistryFriendlyByteBuf, CollapsedBacteriaStats> STREAM_CODEC =
            ByteBufCodecs.registry(NTRegistries.BACTERIA_STATS_SERIALIZER_KEY).dispatch(CollapsedBacteriaStats::getSerializer, BacteriaStatsSerializer::collapsedStreamCodec);

    float growthRate();

    float mutationResistance();

    float productionRate();

    int lifespan();

    int color();

    CollapsedBacteriaStats rollStats();

    List<Component> statsTooltip();

    List<Component> statsTooltipWithMutatorValues();

    BacteriaStatsSerializer<?, ?> getSerializer();

    CollapsedBacteriaStats copy();
}
