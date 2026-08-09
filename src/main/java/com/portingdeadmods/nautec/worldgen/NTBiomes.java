package com.portingdeadmods.nautec.worldgen;

import com.portingdeadmods.nautec.datagen.DatapackRegistryProvider;
import com.portingdeadmods.nautec.registries.NTEntities;
import com.portingdeadmods.nautec.registries.NTParticles;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.util.ARGB;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class NTBiomes {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(NTBiomeKeys.ABYSSAL_TRENCH, abyssalTrench(placedFeatures, carvers));
        context.register(NTBiomeKeys.BIOLUMINESCENT_GROVE, bioluminescentGrove(placedFeatures, carvers));
        context.register(NTBiomeKeys.HYDROTHERMAL_VENTS, hydrothermalVents(placedFeatures, carvers));
        context.register(NTBiomeKeys.PRISMARINE_REEF, prismarineReef(placedFeatures, carvers));
    }

    private static Biome abyssalTrench(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.oceanSpawns(mobs, 3, 4, 4);
        mobs.addSpawn(MobCategory.MONSTER, 20, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 1, 3));
        mobs.addSpawn(MobCategory.MONSTER, 25, new MobSpawnSettings.SpawnerData(NTEntities.ABYSSAL_MAW.get(), 1, 2));
        mobs.addSpawn(MobCategory.WATER_AMBIENT, 8, new MobSpawnSettings.SpawnerData(NTEntities.SILT_SKIPPER.get(), 3, 6));

        BiomeGenerationSettings.Builder generation = baseOceanGeneration(placedFeatures, carvers);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DatapackRegistryProvider.PRISMARINE_SAND_OCEAN_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, DatapackRegistryProvider.BUDDING_PRISMARINE_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DatapackRegistryProvider.ABYSSAL_CORAL_PLACE_KEY);

        return baseOceanBiome(0.3F, 0x081A2B)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, ARGB.opaque(0x03080F))
                .setAttribute(EnvironmentAttributes.WATER_FOG_END_DISTANCE, 22.0F)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, ARGB.opaque(0x0A1520))
                .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(NTParticles.ABYSSAL_MOTE.get(), 0.004F))
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    private static Biome bioluminescentGrove(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.oceanSpawns(mobs, 6, 4, 12);
        mobs.addSpawn(MobCategory.WATER_AMBIENT, 20, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 2, 4));
        mobs.addSpawn(MobCategory.WATER_CREATURE, 12, new MobSpawnSettings.SpawnerData(NTEntities.LANTERN_JELLY.get(), 1, 3));
        mobs.addSpawn(MobCategory.WATER_AMBIENT, 15, new MobSpawnSettings.SpawnerData(NTEntities.SILT_SKIPPER.get(), 4, 8));

        BiomeGenerationSettings.Builder generation = baseOceanGeneration(placedFeatures, carvers);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DatapackRegistryProvider.DEEP_KELP_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DatapackRegistryProvider.LUMINESCENT_ALGAE_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, DatapackRegistryProvider.GLOW_POLYP_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, DatapackRegistryProvider.BUDDING_PRISMARINE_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DatapackRegistryProvider.PRISMARINE_SAND_OCEAN_PLACE_KEY);

        return baseOceanBiome(0.5F, 0x1C8C81)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, ARGB.opaque(0x0B4F4A))
                .setAttribute(EnvironmentAttributes.WATER_FOG_END_DISTANCE, 130.0F)
                .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(NTParticles.GLOW_SPORE.get(), 0.0035F))
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    private static Biome hydrothermalVents(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.oceanSpawns(mobs, 4, 3, 6);
        mobs.addSpawn(MobCategory.WATER_CREATURE, 20, new MobSpawnSettings.SpawnerData(NTEntities.VENT_CRAWLER.get(), 1, 3));

        BiomeGenerationSettings.Builder generation = baseOceanGeneration(placedFeatures, carvers);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DatapackRegistryProvider.VENT_BASALT_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DatapackRegistryProvider.VENT_MAGMA_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DatapackRegistryProvider.VENT_TUBEWORM_PLACE_KEY);

        return baseOceanBiome(0.85F, 0x4A3527)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, ARGB.opaque(0x2B1C12))
                .setAttribute(EnvironmentAttributes.WATER_FOG_END_DISTANCE, 48.0F)
                .setAttribute(EnvironmentAttributes.AMBIENT_PARTICLES, AmbientParticle.of(NTParticles.VENT_BUBBLE.get(), 0.012F))
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    private static Biome prismarineReef(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.warmOceanSpawns(mobs, 10, 4);
        mobs.addSpawn(MobCategory.WATER_AMBIENT, 25, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 8, 8))
                .addSpawn(MobCategory.WATER_CREATURE, 3, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 1, 2))
                .addSpawn(MobCategory.WATER_AMBIENT, 12, new MobSpawnSettings.SpawnerData(NTEntities.SILT_SKIPPER.get(), 4, 8));

        BiomeGenerationSettings.Builder generation = baseOceanGeneration(placedFeatures, carvers);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_WARM);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DatapackRegistryProvider.PRISMARINE_FROND_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DatapackRegistryProvider.REEF_PRISMARINE_PLACE_KEY);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DatapackRegistryProvider.PRISMARINE_SAND_OCEAN_PLACE_KEY);

        return baseOceanBiome(0.9F, 0x25C4B4)
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, ARGB.opaque(0x1B9A90))
                .setAttribute(EnvironmentAttributes.WATER_FOG_END_DISTANCE, 145.0F)
                .mobSpawnSettings(mobs.build())
                .generationSettings(generation.build())
                .build();
    }

    private static Biome.BiomeBuilder baseOceanBiome(float temperature, int waterColor) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(temperature)
                .downfall(0.5F)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(temperature))
                .specialEffects(new BiomeSpecialEffects.Builder().waterColor(waterColor).build());
    }

    private static BiomeGenerationSettings.Builder baseOceanGeneration(HolderGetter<PlacedFeature> placedFeatures,
                                                                      HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generation);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generation);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);
        BiomeDefaultFeatures.addSurfaceFreezing(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        return generation;
    }

    private NTBiomes() {
    }
}
