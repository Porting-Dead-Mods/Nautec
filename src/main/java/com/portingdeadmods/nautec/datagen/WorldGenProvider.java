package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfigKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, name));
    }
    public static ResourceKey<PlacedFeature> registerPlaceKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, name));
    }

    public WorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Nautec.MODID));
    }

    // Beach - Higher Spawn Chance - 1 to 4 Ores in a Vein
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMARINE_SAND_BEACH_KEY = registerConfigKey("prismarine_sand_beach");
    public static final ResourceKey<PlacedFeature> PRISMARINE_SAND_BEACH_PLACE_KEY = registerPlaceKey("prismarine_sand_beach");

    // Ocean - Lower Spawn Chance - Larger Veins
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMARINE_SAND_OCEAN_KEY = registerConfigKey("prismarine_sand_ocean");
    public static final ResourceKey<PlacedFeature> PRISMARINE_SAND_OCEAN_PLACE_KEY = registerPlaceKey("prismarine_sand_ocean");

    private static final RuleTest SAND_REPLACEABLES = new BlockMatchTest(Blocks.SAND);

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()

            // Beach - Higher Spawn Chance - 1 to 4 Ores in a Vein
            .add(Registries.CONFIGURED_FEATURE, context -> {
                List<OreConfiguration.TargetBlockState> prismarine_sand_config = List.of(
                        OreConfiguration.target(SAND_REPLACEABLES, NTBlocks.PRISMARINE_SAND.get().defaultBlockState())
                );

                context.register(PRISMARINE_SAND_BEACH_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(prismarine_sand_config, 6)));
                context.register(PRISMARINE_SAND_OCEAN_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(prismarine_sand_config, 12)));
            })
            .add(Registries.PLACED_FEATURE, context -> {
                HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

                context.register(PRISMARINE_SAND_BEACH_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(PRISMARINE_SAND_BEACH_KEY),
                        List.of(
                                CountPlacement.of(20),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(50),
                                        VerticalAnchor.absolute(68)
                                )
                        )
                ));
                context.register(PRISMARINE_SAND_OCEAN_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(PRISMARINE_SAND_OCEAN_KEY),
                        List.of(
                                CountPlacement.of(10),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(40),
                                        VerticalAnchor.absolute(-15)
                                )
                        )
                ));

                LOGGER.info("Registered Prismarine Sand Generation as a placed feature.");
            });
}
