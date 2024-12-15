package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public record BacteriaStats(Item type,
                            float growthRate,
                            float mutationResistance,
                            float productionRate,
                            int lifespan,
                            int color) {
    public static final Codec<BacteriaStats> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CodecUtils.ITEM_CODEC.fieldOf("type").forGetter(BacteriaStats::type),
                    Codec.FLOAT.fieldOf("growth_rate").forGetter(BacteriaStats::growthRate),
                    Codec.FLOAT.fieldOf("mutation_resistance").forGetter(BacteriaStats::mutationResistance),
                    Codec.FLOAT.fieldOf("production_rate").forGetter(BacteriaStats::productionRate),
                    Codec.INT.fieldOf("lifespan").forGetter(BacteriaStats::lifespan),
                    Codec.INT.fieldOf("color").forGetter(BacteriaStats::color)
            ).apply(instance, BacteriaStats::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BacteriaStats> STREAM_CODEC = StreamCodec.composite(
            CodecUtils.ITEM_STREAM_CODEC,
            BacteriaStats::type,
            ByteBufCodecs.FLOAT,
            BacteriaStats::growthRate,
            ByteBufCodecs.FLOAT,
            BacteriaStats::mutationResistance,
            ByteBufCodecs.FLOAT,
            BacteriaStats::productionRate,
            ByteBufCodecs.INT,
            BacteriaStats::lifespan,
            ByteBufCodecs.INT,
            BacteriaStats::color,
            BacteriaStats::new
    );
}