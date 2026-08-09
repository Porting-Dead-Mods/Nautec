package com.portingdeadmods.nautec.worldgen.injection;

import com.portingdeadmods.nautec.api.worldgen.OceanClimates;
import com.portingdeadmods.nautec.worldgen.NTBiomeKeys;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.function.BiConsumer;

public final class NTOceanRegion {
    private static final List<Slice> SLICES = List.of(
            new Slice(NTBiomeKeys.ABYSSAL_TRENCH,
                    Climate.Parameter.span(-1.0F, -0.15F),
                    OceanClimates.FULL_RANGE,
                    Climate.Parameter.span(-1.05F, -0.85F),
                    OceanClimates.FULL_RANGE,
                    OceanClimates.FULL_RANGE),
            new Slice(NTBiomeKeys.BIOLUMINESCENT_GROVE,
                    OceanClimates.NORMAL_TEMPERATURE,
                    OceanClimates.FULL_RANGE,
                    Climate.Parameter.span(-0.85F, -0.455F),
                    OceanClimates.FULL_RANGE,
                    Climate.Parameter.span(0.05F, 0.45F)),
            new Slice(NTBiomeKeys.HYDROTHERMAL_VENTS,
                    Climate.Parameter.span(0.2F, 1.0F),
                    OceanClimates.FULL_RANGE,
                    OceanClimates.DEEP_OCEAN_CONTINENTALNESS,
                    OceanClimates.FULL_RANGE,
                    Climate.Parameter.span(-0.45F, -0.05F)),
            new Slice(NTBiomeKeys.PRISMARINE_REEF,
                    OceanClimates.WARM_TEMPERATURE,
                    OceanClimates.FULL_RANGE,
                    Climate.Parameter.span(-0.455F, -0.35F),
                    OceanClimates.FULL_RANGE,
                    OceanClimates.FULL_RANGE)
    );

    public static void forEachPoint(BiConsumer<Climate.ParameterPoint, ResourceKey<Biome>> consumer) {
        for (Slice slice : SLICES) {
            consumer.accept(slice.at(OceanClimates.SURFACE_DEPTH), slice.biome());
            consumer.accept(slice.at(OceanClimates.UNDERGROUND_DEPTH), slice.biome());
        }
    }

    public static List<ResourceKey<Biome>> biomes() {
        return SLICES.stream().map(Slice::biome).toList();
    }

    private record Slice(ResourceKey<Biome> biome,
                         Climate.Parameter temperature,
                         Climate.Parameter humidity,
                         Climate.Parameter continentalness,
                         Climate.Parameter erosion,
                         Climate.Parameter weirdness) {
        Climate.ParameterPoint at(Climate.Parameter depth) {
            return new Climate.ParameterPoint(temperature, humidity, continentalness, erosion, depth, weirdness, 0L);
        }
    }

    private NTOceanRegion() {
    }
}
