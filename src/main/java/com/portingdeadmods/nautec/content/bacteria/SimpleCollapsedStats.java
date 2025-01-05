package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.api.bacteria.CollapsedBacteriaStats;
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
 *
 * @param growthRate
 * @param mutationResistance
 * @param productionRate
 * @param lifespan
 * @param color
 */
public record SimpleCollapsedStats(float growthRate,
                                   float mutationResistance,
                                   float productionRate,
                                   int lifespan,
                                   int color) implements CollapsedBacteriaStats {
    public static final SimpleCollapsedStats EMPTY = new SimpleCollapsedStats(0, 0, 0, 0, -1);

    public static SimpleCollapsedStats from(BacteriaStats<?> stats) {
        return new SimpleCollapsedStats(
                RNGUtils.uniformRandFloat(stats.growthRate()),
                RNGUtils.uniformRandFloat(stats.mutationResistance()),
                RNGUtils.uniformRandFloat(stats.productionRate()),
                RNGUtils.uniformRandInt(stats.lifespan()),
                stats.color()
        );
    }

    public SimpleCollapsedStats getMaxStats() {
        return new SimpleCollapsedStats(
                bacteriaGrowthRateCap,
                bacteriaMutationResistanceCap,
                bacteriaProductionRateCap,
                bacteriaLifespanCap,
                this.color
        );
    }

    public static final MapCodec<SimpleCollapsedStats> CODEC =
            RecordCodecBuilder.mapCodec(
                    instance -> instance.group(
                            Codec.FLOAT.fieldOf("growth_rate").forGetter(SimpleCollapsedStats::growthRate),
                            Codec.FLOAT.fieldOf("mutation_resistance").forGetter(SimpleCollapsedStats::mutationResistance),
                            Codec.FLOAT.fieldOf("production_rate").forGetter(SimpleCollapsedStats::productionRate),
                            Codec.INT.fieldOf("lifespan").forGetter(SimpleCollapsedStats::lifespan),
                            Codec.INT.fieldOf("color").forGetter(SimpleCollapsedStats::color)
                    ).apply(instance, SimpleCollapsedStats::new)
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleCollapsedStats> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    SimpleCollapsedStats::growthRate,
                    ByteBufCodecs.FLOAT,
                    SimpleCollapsedStats::mutationResistance,
                    ByteBufCodecs.FLOAT,
                    SimpleCollapsedStats::productionRate,
                    ByteBufCodecs.INT,
                    SimpleCollapsedStats::lifespan,
                    ByteBufCodecs.INT,
                    SimpleCollapsedStats::color,
                    SimpleCollapsedStats::new
            );

    public static class Serializer {
        public static final Serializer INSTANCE = new Serializer();

        public MapCodec<SimpleCollapsedStats> mapCodec() {
            return SimpleCollapsedStats.CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, SimpleCollapsedStats> streamCodec() {
            return SimpleCollapsedStats.STREAM_CODEC;
        }
    }

    public static Serializer getSerializer() {
        return Serializer.INSTANCE;
    }

    public SimpleCollapsedStats rollGrowthRate() {
        float newGR = growthRate + (RNGUtils.floatInRangeOf(growthRate) / (10 * (1 + mutationResistance)));
        newGR = Math.max(newGR, 0);

        newGR = Math.min(newGR, bacteriaGrowthRateCap);

        return new SimpleCollapsedStats(newGR, mutationResistance, productionRate, lifespan, color);
    }

    public SimpleCollapsedStats rollMutationResistance() {
        float newMR = mutationResistance + RNGUtils.biasedInRange(0, 0.1f, mutationResistance / bacteriaMutationResistanceCap) / 10 * (bacteriaMutationResistanceCap - mutationResistance);
        newMR = Math.max(newMR, 0);

        return new SimpleCollapsedStats(growthRate, newMR, productionRate, lifespan, color);
    }

    public SimpleCollapsedStats rollProductionRate() {
        float newPR = productionRate + RNGUtils.floatInRangeOf(productionRate) / 10 * (1 + mutationResistance);
        newPR = Math.max(newPR, 0);

        newPR = Math.min(newPR, bacteriaProductionRateCap);

        return new SimpleCollapsedStats(growthRate, mutationResistance, newPR, lifespan, color);
    }

    public SimpleCollapsedStats rollLifespan() {
        int newLS = lifespan + (int) (RNGUtils.uniformRandInt(-100, 100) / (1 + mutationResistance));
        newLS = Math.max(newLS, 0);

        newLS = Math.min(newLS, bacteriaLifespanCap);

        return new SimpleCollapsedStats(growthRate, mutationResistance, productionRate, newLS, color);
    }

    public SimpleCollapsedStats rollStats() {
        return rollGrowthRate().rollMutationResistance().rollProductionRate().rollLifespan();
    }

    // TODO: Might wanna move this to SimpleBacteriaInstance
    public SimpleCollapsedStats grow() {
//        float newCS = colonySize + RNGUtils.floatInRangeOf(0, growthRate);
//        newCS = Math.min(newCS, bacteriaColonySizeCap);
//
//        return new BacteriaStats(growthRate, mutationResistance, productionRate, newCS, lifespan, color);
        return this;
    }

    public SimpleCollapsedStats shrink() {
//        float newCS = colonySize - colonySize * RNGUtils.uniformRandFloat(25) - 1;
//        return new BacteriaStats(growthRate, mutationResistance, productionRate, newCS, lifespan, color);
        return this;
    }

    public SimpleCollapsedStats copy() {
        return new SimpleCollapsedStats(growthRate, mutationResistance, productionRate, lifespan, color);
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
