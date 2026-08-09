package com.portingdeadmods.nautec.gametest.suite;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.api.worldgen.OceanClimates;
import com.portingdeadmods.nautec.worldgen.injection.NTOceanRegion;
import com.portingdeadmods.nautec.worldgen.injection.ParameterListMerger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class WorldgenInjectionTests {
    private static final ResourceKey<Biome> VANILLA = ResourceKey.create(Registries.BIOME, Identifier.withDefaultNamespace("deep_ocean"));

    public static void register(NTTestRegistrar r) {
        r.add("worldgen/overworld_preset_contains_our_biomes", 20, helper -> {
            HolderLookup.RegistryLookup<Biome> biomes = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME);

            for (ResourceKey<Biome> key : NTOceanRegion.biomes()) {
                if (biomes.get(key).isEmpty()) {
                    helper.fail("Biome " + key.identifier() + " is not loaded, its datapack entry is missing");
                }
            }

            MultiNoiseBiomeSourceParameterList list =
                    new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD, biomes);

            Set<ResourceKey<Biome>> present = list.parameters().values().stream()
                    .map(Pair::getSecond)
                    .map(Holder::getKey)
                    .collect(Collectors.toSet());

            for (ResourceKey<Biome> key : NTOceanRegion.biomes()) {
                if (!present.contains(key)) {
                    helper.fail("The overworld preset does not contain " + key.identifier() + " after injection");
                }
            }

            if (!present.contains(Biomes.DEEP_OCEAN) || !present.contains(Biomes.WARM_OCEAN)) {
                helper.fail("Injection removed a vanilla ocean biome from the preset entirely");
            }
            helper.succeed();
        });

        r.add("worldgen/merger_leaves_disjoint_points_alone", 5, helper -> {
            Climate.ParameterPoint target = point(OceanClimates.FROZEN_TEMPERATURE, OceanClimates.DEEP_OCEAN_CONTINENTALNESS, OceanClimates.SURFACE_DEPTH);
            Climate.ParameterPoint cut = point(OceanClimates.WARM_TEMPERATURE, OceanClimates.DEEP_OCEAN_CONTINENTALNESS, OceanClimates.SURFACE_DEPTH);

            List<Climate.ParameterPoint> remainder = ParameterListMerger.subtract(target, cut);
            if (remainder.size() != 1 || !remainder.getFirst().equals(target)) {
                helper.fail("A non-overlapping cut must leave the point untouched, got " + remainder);
            }
            helper.succeed();
        });

        r.add("worldgen/merger_removes_fully_covered_points", 5, helper -> {
            Climate.ParameterPoint target = point(OceanClimates.NORMAL_TEMPERATURE, Climate.Parameter.span(-0.8F, -0.5F), OceanClimates.SURFACE_DEPTH);
            Climate.ParameterPoint cut = point(OceanClimates.FULL_RANGE, OceanClimates.FULL_RANGE, OceanClimates.SURFACE_DEPTH);

            List<Climate.ParameterPoint> remainder = ParameterListMerger.subtract(target, cut);
            if (!remainder.isEmpty()) {
                helper.fail("A cut covering the whole point must remove it, got " + remainder);
            }
            helper.succeed();
        });

        r.add("worldgen/merger_depth_variants_are_independent", 5, helper -> {
            Climate.ParameterPoint underground = point(OceanClimates.NORMAL_TEMPERATURE, OceanClimates.DEEP_OCEAN_CONTINENTALNESS, OceanClimates.UNDERGROUND_DEPTH);
            Climate.ParameterPoint surfaceCut = point(OceanClimates.FULL_RANGE, OceanClimates.FULL_RANGE, OceanClimates.SURFACE_DEPTH);

            List<Climate.ParameterPoint> remainder = ParameterListMerger.subtract(underground, surfaceCut);
            if (remainder.size() != 1) {
                helper.fail("A surface cut must not touch the underground copy of a biome, got " + remainder);
            }
            helper.succeed();
        });

        r.add("worldgen/merger_preserves_coverage", 5, helper -> {
            Climate.ParameterPoint vanilla = point(OceanClimates.NORMAL_TEMPERATURE, OceanClimates.DEEP_OCEAN_CONTINENTALNESS, OceanClimates.SURFACE_DEPTH);
            Climate.ParameterPoint cut = point(OceanClimates.NORMAL_TEMPERATURE, Climate.Parameter.span(-0.85F, -0.455F), OceanClimates.SURFACE_DEPTH);

            List<Climate.ParameterPoint> remainder = ParameterListMerger.subtract(vanilla, cut);
            long covered = remainder.stream().mapToLong(WorldgenInjectionTests::volume).sum() + volume(intersection(vanilla, cut));
            if (covered != volume(vanilla)) {
                helper.fail("Carving lost or duplicated climate volume: " + covered + " != " + volume(vanilla));
            }
            for (Climate.ParameterPoint piece : remainder) {
                if (overlaps(piece, cut)) {
                    helper.fail("A carved piece still overlaps the injected slice: " + piece);
                }
            }
            helper.succeed();
        });

        r.add("worldgen/injected_slices_resolve_to_our_biomes", 5, helper -> {
            List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> injected = new ArrayList<>();
            NTOceanRegion.forEachPoint((p, key) -> injected.add(Pair.of(p, key)));

            if (injected.size() != NTOceanRegion.biomes().size() * 2) {
                helper.fail("Every slice must be injected at both the surface and underground depth, got " + injected.size());
            }

            Climate.ParameterList<ResourceKey<Biome>> base = new Climate.ParameterList<>(List.of(
                    Pair.of(point(OceanClimates.FULL_RANGE, OceanClimates.DEEP_OCEAN_CONTINENTALNESS, OceanClimates.SURFACE_DEPTH), VANILLA),
                    Pair.of(point(OceanClimates.FULL_RANGE, OceanClimates.DEEP_OCEAN_CONTINENTALNESS, OceanClimates.UNDERGROUND_DEPTH), VANILLA),
                    Pair.of(point(OceanClimates.FULL_RANGE, OceanClimates.OCEAN_CONTINENTALNESS, OceanClimates.SURFACE_DEPTH), VANILLA),
                    Pair.of(point(OceanClimates.FULL_RANGE, OceanClimates.OCEAN_CONTINENTALNESS, OceanClimates.UNDERGROUND_DEPTH), VANILLA)
            ));

            Climate.ParameterList<ResourceKey<Biome>> merged = ParameterListMerger.carveAndAppend(base, injected);

            for (Pair<Climate.ParameterPoint, ResourceKey<Biome>> slice : injected) {
                ResourceKey<Biome> found = merged.findValue(centre(slice.getFirst()));
                if (!slice.getSecond().equals(found)) {
                    helper.fail("The centre of " + slice.getSecond().identifier() + "'s slice resolved to " + found.identifier());
                }
            }

            Climate.TargetPoint outside = Climate.target(0.0F, 0.0F, -0.25F, 0.0F, 0.0F, 0.9F);
            if (!VANILLA.equals(merged.findValue(outside))) {
                helper.fail("A climate outside every injected slice must still resolve to the vanilla biome");
            }
            helper.succeed();
        });
    }

    private static Climate.ParameterPoint point(Climate.Parameter temperature, Climate.Parameter continentalness, Climate.Parameter depth) {
        return new Climate.ParameterPoint(temperature, OceanClimates.FULL_RANGE, continentalness, OceanClimates.FULL_RANGE, depth, OceanClimates.FULL_RANGE, 0L);
    }

    private static Climate.TargetPoint centre(Climate.ParameterPoint point) {
        return new Climate.TargetPoint(
                mid(point.temperature()), mid(point.humidity()), mid(point.continentalness()),
                mid(point.erosion()), mid(point.depth()), mid(point.weirdness()));
    }

    private static long mid(Climate.Parameter parameter) {
        return parameter.min() + (parameter.max() - parameter.min()) / 2L;
    }

    private static long volume(Climate.ParameterPoint point) {
        return span(point.temperature()) * span(point.humidity()) * span(point.continentalness())
                * span(point.erosion()) * span(point.depth()) * span(point.weirdness());
    }

    private static long span(Climate.Parameter parameter) {
        return parameter.max() - parameter.min() + 1L;
    }

    private static Climate.ParameterPoint intersection(Climate.ParameterPoint a, Climate.ParameterPoint b) {
        return new Climate.ParameterPoint(
                overlap(a.temperature(), b.temperature()),
                overlap(a.humidity(), b.humidity()),
                overlap(a.continentalness(), b.continentalness()),
                overlap(a.erosion(), b.erosion()),
                overlap(a.depth(), b.depth()),
                overlap(a.weirdness(), b.weirdness()),
                0L);
    }

    private static Climate.Parameter overlap(Climate.Parameter a, Climate.Parameter b) {
        return new Climate.Parameter(Math.max(a.min(), b.min()), Math.min(a.max(), b.max()));
    }

    private static boolean overlaps(Climate.ParameterPoint a, Climate.ParameterPoint b) {
        return overlaps(a.temperature(), b.temperature()) && overlaps(a.humidity(), b.humidity())
                && overlaps(a.continentalness(), b.continentalness()) && overlaps(a.erosion(), b.erosion())
                && overlaps(a.depth(), b.depth()) && overlaps(a.weirdness(), b.weirdness());
    }

    private static boolean overlaps(Climate.Parameter a, Climate.Parameter b) {
        return a.min() <= b.max() && b.min() <= a.max();
    }

    private WorldgenInjectionTests() {
    }
}
