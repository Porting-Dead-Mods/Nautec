package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.utils.ComponentUtils;
import com.portingdeadmods.nautec.utils.MathUtils;
import com.portingdeadmods.nautec.utils.RNGUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

import static com.portingdeadmods.nautec.NTConfig.*;
import static com.portingdeadmods.nautec.NTConfig.bacteriaLifespanCap;

/**
 * Collapses the ranges of a {@link BacteriaStats} instance.
 * @param growthRate
 * @param mutationResistance
 * @param productionRate
 * @param lifespan
 * @param color
 */
public record CollapsedStats(float growthRate,
                             float mutationResistance,
                             float productionRate,
                             int lifespan,
                             int color) {

    public static CollapsedStats from(BacteriaStats stats) {
        return new CollapsedStats(
                RNGUtils.uniformRandFloat(stats.growthRate().get(0), stats.growthRate().get(1)),
                RNGUtils.uniformRandFloat(stats.mutationResistance().get(0), stats.mutationResistance().get(1)),
                RNGUtils.uniformRandFloat(stats.productionRate().get(0), stats.productionRate().get(1)),
                RNGUtils.uniformRandInt(stats.lifespan().get(0), stats.lifespan().get(1)),
                stats.color()
        );
    }

    public static final CollapsedStats EMPTY = new CollapsedStats(0, 0, 0, 0, -1);

    public CollapsedStats getMaxStats() { return new CollapsedStats(
            bacteriaGrowthRateCap,
            bacteriaMutationResistanceCap,
            bacteriaProductionRateCap,
            bacteriaLifespanCap,
            this.color
    );
    }

    public static final MapCodec<CollapsedStats> CODEC =
            RecordCodecBuilder.mapCodec(
                    instance -> instance.group(
                            Codec.FLOAT.fieldOf("growth_rate").forGetter(CollapsedStats::growthRate),
                            Codec.FLOAT.fieldOf("mutation_resistance").forGetter(CollapsedStats::mutationResistance),
                            Codec.FLOAT.fieldOf("production_rate").forGetter(CollapsedStats::productionRate),
                            Codec.INT.fieldOf("lifespan").forGetter(CollapsedStats::lifespan),
                            Codec.INT.fieldOf("color").forGetter(CollapsedStats::color)
                    ).apply(instance, CollapsedStats::new)
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, CollapsedStats> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    CollapsedStats::growthRate,
                    ByteBufCodecs.FLOAT,
                    CollapsedStats::mutationResistance,
                    ByteBufCodecs.FLOAT,
                    CollapsedStats::productionRate,
                    ByteBufCodecs.INT,
                    CollapsedStats::lifespan,
                    ByteBufCodecs.INT,
                    CollapsedStats::color,
                    CollapsedStats::new
            );

    public static class Serializer {
        public static final Serializer INSTANCE = new Serializer();

        public MapCodec<CollapsedStats> mapCodec() {
            return CollapsedStats.CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, CollapsedStats> streamCodec() {
            return CollapsedStats.STREAM_CODEC;
        }
    }

    public static Serializer getSerializer() {
        return Serializer.INSTANCE;
    }

    public CollapsedStats rollGrowthRate() {
        float newGR = growthRate + (RNGUtils.floatInRangeOf(growthRate) / (10 * (1 + mutationResistance)));
        newGR = Math.max(newGR, 0);

        newGR = Math.min(newGR, bacteriaGrowthRateCap);

        return new CollapsedStats(newGR, mutationResistance, productionRate, lifespan, color);
    }

    public CollapsedStats rollMutationResistance() {
        float newMR = mutationResistance + RNGUtils.biasedInRange(0, 0.1f, mutationResistance / bacteriaMutationResistanceCap) / 10 * (bacteriaMutationResistanceCap - mutationResistance);
        newMR = Math.max(newMR, 0);

        return new CollapsedStats(growthRate, newMR, productionRate, lifespan, color);
    }

    public CollapsedStats rollProductionRate() {
        float newPR = productionRate + RNGUtils.floatInRangeOf(productionRate) / 10 * (1 + mutationResistance);
        newPR = Math.max(newPR, 0);

        newPR = Math.min(newPR, bacteriaProductionRateCap);

        return new CollapsedStats(growthRate, mutationResistance, newPR, lifespan, color);
    }

    public CollapsedStats rollLifespan() {
        int newLS = lifespan + (int) (RNGUtils.uniformRandInt(-100, 100) / (1 + mutationResistance));
        newLS = Math.max(newLS, 0);

        newLS = Math.min(newLS, bacteriaLifespanCap);

        return new CollapsedStats(growthRate, mutationResistance, productionRate, newLS, color);
    }

    public CollapsedStats rollStats() {
        return rollGrowthRate().rollMutationResistance().rollProductionRate().rollLifespan();
    }

    // TODO: Might wanna move this to SimpleBacteriaInstance
    public CollapsedStats grow() {
//        float newCS = colonySize + RNGUtils.floatInRangeOf(0, growthRate);
//        newCS = Math.min(newCS, bacteriaColonySizeCap);
//
//        return new BacteriaStats(growthRate, mutationResistance, productionRate, newCS, lifespan, color);
        return this;
    }

    public CollapsedStats shrink() {
//        float newCS = colonySize - colonySize * RNGUtils.uniformRandFloat(25) - 1;
//        return new BacteriaStats(growthRate, mutationResistance, productionRate, newCS, lifespan, color);
        return this;
    }

    public CollapsedStats copy() {
        return new CollapsedStats(growthRate, mutationResistance, productionRate, lifespan, color);
    }

    public List<Component> statsTooltip() {
        return List.of(
                ComponentUtils.countableStatShow("Growth Rate", growthRate, bacteriaGrowthRateCap),
                ComponentUtils.countableStatShow("Mutation Resistance", mutationResistance, bacteriaMutationResistanceCap),
                ComponentUtils.countableStatShow("Production Rate", productionRate, bacteriaProductionRateCap),
                ComponentUtils.countableStatShow("Lifespan", lifespan, bacteriaLifespanCap)
        );
    }

    public List<Component> statsTooltipWithMutatorValues() {
        final MutableComponent arrow = ComponentUtils.colored(" -> ", ChatFormatting.YELLOW);

        return List.of(
                ComponentUtils.countableStatShow("Growth Rate", growthRate, bacteriaGrowthRateCap)
                        .append(arrow)
                        .append(ComponentUtils.colored("[", ChatFormatting.GREEN))
                        .append(ComponentUtils.statRange(
                                MathUtils.roundToPrecision(growthRate + (-growthRate / (10 * (1 + mutationResistance))), 2),
                                MathUtils.roundToPrecision(growthRate + (growthRate / (10 * (1 + mutationResistance))), 2),
                                bacteriaGrowthRateCap))
                        .append(ComponentUtils.colored("]", ChatFormatting.GREEN)),
                ComponentUtils.countableStatShow("Mutation Resistance", mutationResistance, bacteriaMutationResistanceCap)
                        .append(arrow)
                        .append(ComponentUtils.colored("[", ChatFormatting.GREEN))
                        .append(ComponentUtils.statRange(
                                MathUtils.roundToPrecision(mutationResistance + 0.05f * (1 - mutationResistance / bacteriaMutationResistanceCap), 2),
                                MathUtils.roundToPrecision(mutationResistance + 0.1f, 2),
                                bacteriaMutationResistanceCap))
                        .append(ComponentUtils.colored("]", ChatFormatting.GREEN)),
                ComponentUtils.countableStatShow("Production Rate", productionRate, bacteriaProductionRateCap)
                        .append(arrow)
                        .append(ComponentUtils.colored("[", ChatFormatting.GREEN))
                        .append(ComponentUtils.statRange(
                                MathUtils.roundToPrecision(productionRate + (-productionRate / (10 * (1 + mutationResistance))), 2),
                                MathUtils.roundToPrecision(productionRate + (productionRate / (10 * (1 + mutationResistance))), 2),
                                bacteriaProductionRateCap))
                        .append(ComponentUtils.colored("]", ChatFormatting.GREEN)),
                ComponentUtils.countableStatShow("Lifespan", lifespan, bacteriaLifespanCap)
                        .append(arrow)
                        .append(ComponentUtils.colored("[", ChatFormatting.GREEN))
                        .append(ComponentUtils.statRange(
                                lifespan + (int) (-100 / (1 + mutationResistance)),
                                lifespan + (int) (100 / (1 + mutationResistance)),
                                bacteriaLifespanCap))
                        .append(ComponentUtils.colored("]", ChatFormatting.GREEN))
        );
    }
}
