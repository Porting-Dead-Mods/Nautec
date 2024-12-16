package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.BaseBacteriaStats;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

import java.util.List;

public record BacteriaStats(Item resource,
                            float growthRate,
                            float mutationResistance,
                            float productionRate,
                            int lifespan,
                            int color) implements BaseBacteriaStats {
    public static final Codec<BacteriaStats> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CodecUtils.ITEM_CODEC.fieldOf("resource").forGetter(BacteriaStats::resource),
                    Codec.FLOAT.fieldOf("growth_rate").forGetter(BacteriaStats::growthRate),
                    Codec.FLOAT.fieldOf("mutation_resistance").forGetter(BacteriaStats::mutationResistance),
                    Codec.FLOAT.fieldOf("production_rate").forGetter(BacteriaStats::productionRate),
                    Codec.INT.fieldOf("lifespan").forGetter(BacteriaStats::lifespan),
                    Codec.INT.fieldOf("color").forGetter(BacteriaStats::color)
            ).apply(instance, BacteriaStats::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BacteriaStats> STREAM_CODEC = StreamCodec.composite(
            CodecUtils.ITEM_STREAM_CODEC,
            BacteriaStats::resource,
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

    @Override
    public List<Component> statsTooltip() {
        return List.of(
                Component.literal("Resource: "+resource.getDefaultInstance().getDisplayName().getString()),
                Component.literal("Growth Rate: "+growthRate)
        );
    }
}
