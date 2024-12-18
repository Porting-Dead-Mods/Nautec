package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.BaseBacteriaStats;
import com.portingdeadmods.nautec.utils.ComponentUtils;
import com.portingdeadmods.nautec.utils.RNGUtils;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

import static com.portingdeadmods.nautec.NTConfig.*;

import java.util.List;

public record BacteriaStats(Item resource,
                            float growthRate,
                            float mutationResistance,
                            float productionRate,
                            float colonySize,
                            int lifespan,
                            int color) implements BaseBacteriaStats {
    public static final Codec<BacteriaStats> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CodecUtils.ITEM_CODEC.fieldOf("resource").forGetter(BacteriaStats::resource),
                    Codec.FLOAT.fieldOf("growth_rate").forGetter(BacteriaStats::growthRate),
                    Codec.FLOAT.fieldOf("mutation_resistance").forGetter(BacteriaStats::mutationResistance),
                    Codec.FLOAT.fieldOf("production_rate").forGetter(BacteriaStats::productionRate),
                    Codec.FLOAT.fieldOf("colony_size").forGetter(BacteriaStats::colonySize),
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
            ByteBufCodecs.FLOAT,
            BacteriaStats::colonySize,
            ByteBufCodecs.INT,
            BacteriaStats::lifespan,
            ByteBufCodecs.INT,
            BacteriaStats::color,
            BacteriaStats::new
    );

    @Override
    public List<Component> statsTooltip() {
        return List.of(
                ComponentUtils.stringStatShow("Resource", resource.getDefaultInstance().getDisplayName().getString()),
                ComponentUtils.countableStatShow("Growth Rate", growthRate, bacteriaGrowthRateCap),
                ComponentUtils.countableStatShow("Mutation Resistance", mutationResistance, bacteriaMutationResistanceCap),
                ComponentUtils.countableStatShow("Production Rate", productionRate, bacteriaProductionRateCap),
                ComponentUtils.countableStatShow("Colony Size", colonySize, bacteriaColonySizeCap),
                ComponentUtils.countableStatShow("Lifespan", lifespan, bacteriaLifespanCap)
        );
    }

    @Override
    public BaseBacteriaStats rollGrowthRate() {
        float newGR = growthRate + (RNGUtils.floatInRangeOf(growthRate) / (10 * (1 + mutationResistance)));
        newGR = Math.min(newGR, bacteriaGrowthRateCap);

        return new BacteriaStats(resource, newGR, mutationResistance, productionRate, colonySize, lifespan, color);
    }

    @Override
    public BaseBacteriaStats rollMutationResistance() {
        float newMR = mutationResistance + RNGUtils.biasedInRange(0, 0.1f, mutationResistance) / 10 * (bacteriaMutationResistanceCap - mutationResistance);

        return new BacteriaStats(resource, growthRate, newMR, productionRate, colonySize, lifespan, color);
    }

    @Override
    public BaseBacteriaStats rollProductionRate() {
        float newPR = productionRate + RNGUtils.floatInRangeOf(productionRate) / 10 * (1 + mutationResistance);
        newPR = Math.min(newPR, bacteriaProductionRateCap);

        return new BacteriaStats(resource, growthRate, mutationResistance, newPR, colonySize, lifespan, color);
    }

    @Override
    public BaseBacteriaStats rollLifespan() {
        int newLS = lifespan + (int) (RNGUtils.uniformRandInt(-10, 10) / (1 + mutationResistance));
        newLS = Math.min(newLS, bacteriaLifespanCap);

        return new BacteriaStats(resource, growthRate, mutationResistance, productionRate, colonySize, newLS, color);
    }

    @Override
    public BaseBacteriaStats rollStats() {
        return rollGrowthRate().rollMutationResistance().rollProductionRate().rollLifespan();
    }

    @Override
    public BaseBacteriaStats grow() {
        float newCS = colonySize + RNGUtils.floatInRangeOf(0, growthRate);
        newCS = Math.min(newCS, bacteriaColonySizeCap);

        return new BacteriaStats(resource, growthRate, mutationResistance, productionRate, newCS, lifespan, color);
    }

    @Override
    public BaseBacteriaStats shrink() {
        float newCS = colonySize - colonySize * RNGUtils.uniformRandFloat(25) - 1;
        return new BacteriaStats(resource, growthRate, mutationResistance, productionRate, newCS, lifespan, color);
    }
}
