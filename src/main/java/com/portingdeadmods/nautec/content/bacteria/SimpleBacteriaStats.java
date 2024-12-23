package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStatsSerializer;
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

public record SimpleBacteriaStats(float growthRate,
                                  float mutationResistance,
                                  float productionRate,
                                  int lifespan,
                                  int color) implements BacteriaStats {
    public static final SimpleBacteriaStats EMPTY = new SimpleBacteriaStats(0, 0, 0, 0, -1);
    public static final MapCodec<SimpleBacteriaStats> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("growth_rate").forGetter(SimpleBacteriaStats::growthRate),
                    Codec.FLOAT.fieldOf("mutation_resistance").forGetter(SimpleBacteriaStats::mutationResistance),
                    Codec.FLOAT.fieldOf("production_rate").forGetter(SimpleBacteriaStats::productionRate),
                    Codec.INT.fieldOf("lifespan").forGetter(SimpleBacteriaStats::lifespan),
                    Codec.INT.fieldOf("color").forGetter(SimpleBacteriaStats::color)
            ).apply(instance, SimpleBacteriaStats::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteriaStats> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            SimpleBacteriaStats::growthRate,
            ByteBufCodecs.FLOAT,
            SimpleBacteriaStats::mutationResistance,
            ByteBufCodecs.FLOAT,
            SimpleBacteriaStats::productionRate,
            ByteBufCodecs.INT,
            SimpleBacteriaStats::lifespan,
            ByteBufCodecs.INT,
            SimpleBacteriaStats::color,
            SimpleBacteriaStats::new
    );

    @Override
    public BacteriaStats copy() {
        return new SimpleBacteriaStats(growthRate, mutationResistance, productionRate, lifespan, color);
    }

    @Override
    public List<Component> statsTooltip() {
        return List.of(
                ComponentUtils.countableStatShow("Growth Rate", growthRate, bacteriaGrowthRateCap),
                ComponentUtils.countableStatShow("Mutation Resistance", mutationResistance, bacteriaMutationResistanceCap),
                ComponentUtils.countableStatShow("Production Rate", productionRate, bacteriaProductionRateCap),
                ComponentUtils.countableStatShow("Lifespan", lifespan, bacteriaLifespanCap)
        );
    }

    @Override
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
                                lifespan + (int) (-lifespan / (1 + mutationResistance)),
                                lifespan + (int) (lifespan / (1 + mutationResistance)),
                                bacteriaLifespanCap))
                        .append(ComponentUtils.colored("]", ChatFormatting.GREEN))
        );
    }

    @Override
    public BacteriaStatsSerializer<SimpleBacteriaStats> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public BacteriaStats rollGrowthRate() {
        float newGR = growthRate + (RNGUtils.floatInRangeOf(growthRate) / (10 * (1 + mutationResistance)));
        newGR = Math.max(newGR, 0);

        newGR = Math.min(newGR, bacteriaGrowthRateCap);

        return new SimpleBacteriaStats(newGR, mutationResistance, productionRate, lifespan, color);
    }

    @Override
    public BacteriaStats rollMutationResistance() {
        float newMR = mutationResistance + RNGUtils.biasedInRange(0, 0.1f, mutationResistance / bacteriaMutationResistanceCap) / 10 * (bacteriaMutationResistanceCap - mutationResistance);
        newMR = Math.max(newMR, 0);

        return new SimpleBacteriaStats(growthRate, newMR, productionRate, lifespan, color);
    }

    @Override
    public BacteriaStats rollProductionRate() {
        float newPR = productionRate + RNGUtils.floatInRangeOf(productionRate) / 10 * (1 + mutationResistance);
        newPR = Math.max(newPR, 0);

        newPR = Math.min(newPR, bacteriaProductionRateCap);

        return new SimpleBacteriaStats(growthRate, mutationResistance, newPR, lifespan, color);
    }

    @Override
    public BacteriaStats rollLifespan() {
        int newLS = lifespan + (int) (RNGUtils.uniformRandInt(-10, 10) / (1 + mutationResistance));
        newLS = Math.max(newLS, 0);

        newLS = Math.min(newLS, bacteriaLifespanCap);

        return new SimpleBacteriaStats(growthRate, mutationResistance, productionRate, newLS, color);
    }

    @Override
    public BacteriaStats rollStats() {
        return rollGrowthRate().rollMutationResistance().rollProductionRate().rollLifespan();
    }

    // TODO: Might wanna move this to SimpleBacteriaInstance
    @Override
    public BacteriaStats grow() {
//        float newCS = colonySize + RNGUtils.floatInRangeOf(0, growthRate);
//        newCS = Math.min(newCS, bacteriaColonySizeCap);
//
//        return new BacteriaStats(growthRate, mutationResistance, productionRate, newCS, lifespan, color);
        return this;
    }

    @Override
    public BacteriaStats shrink() {
//        float newCS = colonySize - colonySize * RNGUtils.uniformRandFloat(25) - 1;
//        return new BacteriaStats(growthRate, mutationResistance, productionRate, newCS, lifespan, color);
        return this;
    }

    public static final class Serializer implements BacteriaStatsSerializer<SimpleBacteriaStats> {
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        @Override
        public MapCodec<SimpleBacteriaStats> mapCodec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SimpleBacteriaStats> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
