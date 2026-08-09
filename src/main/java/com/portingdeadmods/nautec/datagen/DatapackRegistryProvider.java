package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.worldgen.NTBiomes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatapackRegistryProvider extends DatapackBuiltinEntriesProvider {

    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfigKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Nautec.rl(name));
    }
    public static ResourceKey<PlacedFeature> registerPlaceKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Nautec.rl(name));
    }

    public DatapackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Nautec.MODID));
    }

    // Beach - Higher Spawn Chance - 1 to 4 Ores in a Vein
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMARINE_SAND_BEACH_KEY = registerConfigKey("prismarine_sand_beach");
    public static final ResourceKey<PlacedFeature> PRISMARINE_SAND_BEACH_PLACE_KEY = registerPlaceKey("prismarine_sand_beach");

    // Ocean - Lower Spawn Chance - Larger Veins
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMARINE_SAND_OCEAN_KEY = registerConfigKey("prismarine_sand_ocean");
    public static final ResourceKey<PlacedFeature> PRISMARINE_SAND_OCEAN_PLACE_KEY = registerPlaceKey("prismarine_sand_ocean");

    // Hydrothermal Vents floor
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENT_BASALT_KEY = registerConfigKey("vent_basalt");
    public static final ResourceKey<PlacedFeature> VENT_BASALT_PLACE_KEY = registerPlaceKey("vent_basalt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENT_MAGMA_KEY = registerConfigKey("vent_magma");
    public static final ResourceKey<PlacedFeature> VENT_MAGMA_PLACE_KEY = registerPlaceKey("vent_magma");

    // Prismarine Reef outcrops
    public static final ResourceKey<ConfiguredFeature<?, ?>> REEF_PRISMARINE_KEY = registerConfigKey("reef_prismarine");
    public static final ResourceKey<PlacedFeature> REEF_PRISMARINE_PLACE_KEY = registerPlaceKey("reef_prismarine");

    // Flora
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEP_KELP_KEY = registerConfigKey("deep_kelp");
    public static final ResourceKey<PlacedFeature> DEEP_KELP_PLACE_KEY = registerPlaceKey("deep_kelp");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINESCENT_ALGAE_KEY = registerConfigKey("luminescent_algae");
    public static final ResourceKey<PlacedFeature> LUMINESCENT_ALGAE_PLACE_KEY = registerPlaceKey("luminescent_algae");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PRISMARINE_FROND_KEY = registerConfigKey("prismarine_frond");
    public static final ResourceKey<PlacedFeature> PRISMARINE_FROND_PLACE_KEY = registerPlaceKey("prismarine_frond");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENT_TUBEWORM_KEY = registerConfigKey("vent_tubeworm");
    public static final ResourceKey<PlacedFeature> VENT_TUBEWORM_PLACE_KEY = registerPlaceKey("vent_tubeworm");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSSAL_CORAL_KEY = registerConfigKey("abyssal_coral");
    public static final ResourceKey<PlacedFeature> ABYSSAL_CORAL_PLACE_KEY = registerPlaceKey("abyssal_coral");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOW_POLYP_KEY = registerConfigKey("glow_polyp");
    public static final ResourceKey<PlacedFeature> GLOW_POLYP_PLACE_KEY = registerPlaceKey("glow_polyp");

    // Budding Prismarine
    public static final ResourceKey<ConfiguredFeature<?, ?>> BUDDING_PRISMARINE_KEY = registerConfigKey("budding_prismarine");
    public static final ResourceKey<PlacedFeature> BUDDING_PRISMARINE_PLACE_KEY = registerPlaceKey("budding_prismarine");

    private static final RuleTest SAND_REPLACEABLES = new BlockMatchTest(Blocks.SAND);
    private static final RuleTest OCEAN_FLOOR_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
    private static final RuleTest GRAVEL_REPLACEABLES = new BlockMatchTest(Blocks.GRAVEL);

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            // -- BACTERIA --
            .add(NTRegistries.BACTERIA_KEY, NTBacterias::bootstrap)
            // -- WORLDGEN --
            // Beach - Higher Spawn Chance - 1 to 4 Ores in a Vein
            .add(Registries.CONFIGURED_FEATURE, context -> {
                List<OreConfiguration.TargetBlockState> prismarine_sand_config = List.of(
                        OreConfiguration.target(SAND_REPLACEABLES, NTBlocks.PRISMARINE_SAND.get().defaultBlockState())
                );

                context.register(PRISMARINE_SAND_BEACH_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(prismarine_sand_config, 6)));
                context.register(PRISMARINE_SAND_OCEAN_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(prismarine_sand_config, 12)));

                List<OreConfiguration.TargetBlockState> vent_basalt_config = List.of(
                        OreConfiguration.target(OCEAN_FLOOR_REPLACEABLES, Blocks.BASALT.defaultBlockState()),
                        OreConfiguration.target(GRAVEL_REPLACEABLES, Blocks.BASALT.defaultBlockState()),
                        OreConfiguration.target(SAND_REPLACEABLES, Blocks.BASALT.defaultBlockState())
                );
                context.register(VENT_BASALT_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(vent_basalt_config, 33)));

                List<OreConfiguration.TargetBlockState> vent_magma_config = List.of(
                        OreConfiguration.target(OCEAN_FLOOR_REPLACEABLES, Blocks.MAGMA_BLOCK.defaultBlockState()),
                        OreConfiguration.target(GRAVEL_REPLACEABLES, Blocks.MAGMA_BLOCK.defaultBlockState())
                );
                context.register(VENT_MAGMA_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(vent_magma_config, 8)));

                List<OreConfiguration.TargetBlockState> reef_prismarine_config = List.of(
                        OreConfiguration.target(OCEAN_FLOOR_REPLACEABLES, Blocks.PRISMARINE.defaultBlockState()),
                        OreConfiguration.target(SAND_REPLACEABLES, Blocks.PRISMARINE.defaultBlockState()),
                        OreConfiguration.target(GRAVEL_REPLACEABLES, Blocks.PRISMARINE.defaultBlockState())
                );
                context.register(REEF_PRISMARINE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(reef_prismarine_config, 24)));

                context.register(DEEP_KELP_KEY, simpleBlockFeature(NTBlocks.DEEP_KELP.get()));
                context.register(LUMINESCENT_ALGAE_KEY, simpleBlockFeature(NTBlocks.LUMINESCENT_ALGAE.get()));
                context.register(PRISMARINE_FROND_KEY, simpleBlockFeature(NTBlocks.PRISMARINE_FROND.get()));
                context.register(VENT_TUBEWORM_KEY, simpleBlockFeature(NTBlocks.VENT_TUBEWORM.get()));
                context.register(ABYSSAL_CORAL_KEY, simpleBlockFeature(NTBlocks.ABYSSAL_CORAL.get()));
                context.register(GLOW_POLYP_KEY, new ConfiguredFeature<>(Feature.MULTIFACE_GROWTH,
                        new MultifaceGrowthConfiguration(NTBlocks.GLOW_POLYP.get(), 12, true, true, true, 0.5f,
                                HolderSet.direct(Blocks.STONE.builtInRegistryHolder(), Blocks.DEEPSLATE.builtInRegistryHolder(),
                                        Blocks.ANDESITE.builtInRegistryHolder(), Blocks.DIORITE.builtInRegistryHolder(),
                                        Blocks.GRANITE.builtInRegistryHolder(), Blocks.TUFF.builtInRegistryHolder(),
                                        Blocks.PRISMARINE.builtInRegistryHolder(), Blocks.GRAVEL.builtInRegistryHolder()))));

                List<OreConfiguration.TargetBlockState> budding_prismarine_config = List.of(
                        OreConfiguration.target(OCEAN_FLOOR_REPLACEABLES, NTBlocks.BUDDING_PRISMARINE.get().defaultBlockState()),
                        OreConfiguration.target(GRAVEL_REPLACEABLES, NTBlocks.BUDDING_PRISMARINE.get().defaultBlockState())
                );
                context.register(BUDDING_PRISMARINE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(budding_prismarine_config, 3)));
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
                                        VerticalAnchor.absolute(-15),
                                        VerticalAnchor.absolute(40)
                                )
                        )
                ));

                context.register(VENT_BASALT_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(VENT_BASALT_KEY),
                        List.of(
                                CountPlacement.of(24),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-25),
                                        VerticalAnchor.absolute(45)
                                )
                        )
                ));
                context.register(VENT_MAGMA_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(VENT_MAGMA_KEY),
                        List.of(
                                CountPlacement.of(8),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(-25),
                                        VerticalAnchor.absolute(40)
                                )
                        )
                ));
                context.register(REEF_PRISMARINE_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(REEF_PRISMARINE_KEY),
                        List.of(
                                CountPlacement.of(16),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                        VerticalAnchor.absolute(20),
                                        VerticalAnchor.absolute(58)
                                )
                        )
                ));

                context.register(DEEP_KELP_PLACE_KEY, seafloorPlant(configuredFeatures.getOrThrow(DEEP_KELP_KEY), 28));
                context.register(LUMINESCENT_ALGAE_PLACE_KEY, seafloorPlant(configuredFeatures.getOrThrow(LUMINESCENT_ALGAE_KEY), 40));
                context.register(PRISMARINE_FROND_PLACE_KEY, seafloorPlant(configuredFeatures.getOrThrow(PRISMARINE_FROND_KEY), 36));
                context.register(VENT_TUBEWORM_PLACE_KEY, seafloorPlant(configuredFeatures.getOrThrow(VENT_TUBEWORM_KEY), 30));
                context.register(ABYSSAL_CORAL_PLACE_KEY, seafloorPlant(configuredFeatures.getOrThrow(ABYSSAL_CORAL_KEY), 18));
                context.register(GLOW_POLYP_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(GLOW_POLYP_KEY),
                        List.of(
                                CountPlacement.of(64),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(50)),
                                BiomeFilter.biome()
                        )
                ));

                context.register(BUDDING_PRISMARINE_PLACE_KEY, new PlacedFeature(configuredFeatures.getOrThrow(BUDDING_PRISMARINE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(26),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.absolute(-45), VerticalAnchor.absolute(20)),
                                BiomeFilter.biome()
                        )
                ));

                Nautec.LOGGER.info("Registered Prismarine Sand Generation as a placed feature.");
            })
            .add(Registries.BIOME, NTBiomes::bootstrap);

    private static ConfiguredFeature<?, ?> simpleBlockFeature(net.minecraft.world.level.block.Block block) {
        return new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)));
    }

    private static PlacedFeature seafloorPlant(net.minecraft.core.Holder<ConfiguredFeature<?, ?>> feature, int count) {
        return new PlacedFeature(feature, List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        ));
    }
}
