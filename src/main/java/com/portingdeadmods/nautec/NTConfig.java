package com.portingdeadmods.nautec;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = Nautec.MODID)
public final class NTConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue KELP_HEIGHT = BUILDER
            .comment("The height of kelp to be able to grow.")
            .defineInRange("kelpHeight", 40, 25, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue MIXER_POWER_REQUIREMENT = BUILDER
            .comment("The amount of power required by the mixer each tick.")
            .defineInRange("mixerPowerRequirement", 10, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue AUGMENTATION_STATION_POWER_REQUIREMENT = BUILDER
            .comment("The amount of power required by the Augmentation Station each tick.")
            .defineInRange("augmentationPowerRequirement", 25, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue DRAIN_POWER_REQUIREMENT = BUILDER
            .comment("The amount of power required by the Deep Sea Drain each tick.")
            .defineInRange("drainPowerRequirement", 20, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue REGULAR_LASER_DISTANCE = BUILDER
            .comment("The distance of normals lasers.")
            .defineInRange("laserDistance", 16, 0, 128);
    private static final ModConfigSpec.IntValue LONG_DISTANCE_LASER_DISTANCE = BUILDER
            .comment("The distance of Long Distance Laser lasers.")
            .defineInRange("longDistanceLaserDistance", 64, 0, 128);

    private static final ModConfigSpec.IntValue MIXER_INPUT_CAPACITY = BUILDER
            .comment("The capacity of the Mixers Input Tank")
            .defineInRange("mixerInputCapacity", 1_000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MIXER_OUTPUT_CAPACITY = BUILDER
            .comment("The capacity of the Mixers Output Tank")
            .defineInRange("mixerOutputCapacity", 1_000, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue DRAIN_SALT_WATER_AMOUNT = BUILDER
            .comment("The amount of salt water collected by the Deep Sea Drain each second (mb)")
            .defineInRange("drainSaltWaterAmount", 500, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue DRAIN_CAPACITY = BUILDER
            .comment("The fluid capacity of the Deep Sea Drain")
            .defineInRange("drainCapacity", 128_000, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.BooleanValue SPAWN_BOOK_IN_INVENTORY = BUILDER
            .comment("Determines whether to give the player a book when joining a new world")
            .define("spawnBookInInventory", true);
    private static final ModConfigSpec.BooleanValue COLLECT_SALT_WATER = BUILDER
            .comment("Determines whether the player should be able to collect salt water when picking up water in an ocean biome")
            .define("collectSaltWater", true);
    private static final ModConfigSpec.BooleanValue COLLECT_AIR_WITH_BOTTLE = BUILDER
            .comment("Determines whether the player should be able to collect pressurized air bottles by right-clicking on a bubble column")
            .define("collectAirWithBottle", true);

    private static final ModConfigSpec.IntValue GUARDIAN_AUGMENT_DAMAGE = BUILDER
            .comment("The amount of damage the guardian augments laser deals")
            .defineInRange("guardianAugmentDamage", 3, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue ALLOW_AUGMENT_RENDERING = BUILDER
            .comment("Set to false to disable the rendering of augments, this can improve performance")
            .define("allowAugmentRendering", true);

    private static final ModConfigSpec.IntValue FISHER_LASER_LEVEL = BUILDER
            .comment("The amount laser power required to have the fisher work")
            .defineInRange("fisherLaserLevel", 1, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue FISHER_DURATION = BUILDER
            .comment("The amount of ticks the fisher takes to make a new item")
            .defineInRange("fisherRunDuration", 40, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue FISHER_RADIUS = BUILDER
            .comment("The radius on the x and z plane the fisher checks for the water")
            .defineInRange("fisherRadius", 2, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue FISHER_DEPTH = BUILDER
            .comment("The y depth the fisher checks for water")
            .defineInRange("fisherDepth", 2, 1, Integer.MAX_VALUE);

    // Biology
    private static final ModConfigSpec.DoubleValue BACTERIA_GROWTH_RATE_CAP = BUILDER
            .comment("The maximum rate at which bacteria can grow")
            .defineInRange("bacteriaGrowthRateCap", 5, 0, Float.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue BACTERIA_PRODUCTION_RATE_CAP = BUILDER
            .comment("The maximum rate at which bacteria can produce")
            .defineInRange("bacteriaProductionRateCap", 2, 0, Float.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue BACTERIA_MUTATION_RESISTANCE_CAP = BUILDER
            .comment("The maximum rate at which bacteria can resist mutation")
            .defineInRange("bacteriaMutationResistanceCap", 1, 0, Float.MAX_VALUE);

    private static final ModConfigSpec.LongValue BACTERIA_COLONY_SIZE_CAP = BUILDER
            .comment("The maximum size a bacteria colony can grow to")
            .defineInRange("bacteriaColonySizeCap", 40_000, 0, Long.MAX_VALUE);

    private static final ModConfigSpec.IntValue BACTERIA_LIFESPAN_CAP = BUILDER
            .comment("The maximum lifespan of a bacteria colony")
            .defineInRange("bacteriaLifespanCap", 24000, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue BACTERIA_ANALYZER_CRAFTING_SPEED = BUILDER
            .comment("The amount of ticks it takes for the Bacterial Analyzer to analyze a Petri Dish")
            .defineInRange("bacteriaAnalyzerCraftingSpeed", 60, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue BACTERIA_ANALYZER_POWER_USAGE = BUILDER
            .comment("The amount of power used by the Bacterial Analyzer each tick")
            .defineInRange("bacteriaAnalyzerPowerUsage", 5, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue MUTATOR_CRAFTING_SPEED = BUILDER
            .comment("The amount of ticks it takes for the Bacterial Analyzer to analyze a Petri Dish")
            .defineInRange("mutatorCraftingSpeed", 240, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue MUTATOR_POWER_USAGE = BUILDER
            .comment("The amount of power used by the Mutator each tick")
            .defineInRange("mutatorPowerUsage", 10, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue ABYSSAL_EYES_DEPTH = BUILDER
            .comment("The Y level at or below which the Abyssal Eyes augment grants night vision")
            .defineInRange("abyssalEyesDepth", 45, -64, 320);

    private static final ModConfigSpec.DoubleValue PHOTOPHORE_SKIN_RADIUS = BUILDER
            .comment("The radius in which the Photophore Skin augment reveals nearby creatures")
            .defineInRange("photophoreSkinRadius", 12.0, 1.0, 64.0);

    // Worldgen
    private static final ModConfigSpec.BooleanValue ENABLE_BIOME_INJECTION = BUILDER
            .comment("Determines whether Nautec's ocean biomes are added to the world's biome layout. Turning this off leaves vanilla oceans untouched")
            .define("enableBiomeInjection", true);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> INJECTABLE_WORLD_PRESETS = BUILDER
            .comment("The multi-noise presets Nautec's ocean biomes are added to. Packs using a custom overworld preset should list it here")
            .defineList("injectableWorldPresets", List.of("minecraft:overworld"), () -> "minecraft:overworld", entry -> entry instanceof String);



    static final ModConfigSpec SPEC = BUILDER.build();

    public static int kelpHeight;
    public static boolean spawnBookInInventory;
    public static boolean collectSaltWater;
    public static boolean collectAirWithBottle;

    public static int mixerPower;
    public static int drainPower;
    public static int augmentationStationPower;
    public static int laserDistance;
    public static int longDistanceLaserDistance;

    public static int mixerInputCapacity;
    public static int mixerOutputCapacity;

    public static int drainSaltWaterAmount;
    public static int drainCapacity;

    public static int guardianAugmentDamage;
    public static boolean allowAugmentRendering;

    public static int fisherLaserLevel;
    public static int fisherRunDuration;
    public static int fisherRadius;
    public static int fisherDepth;

    public static float bacteriaGrowthRateCap;
    public static float bacteriaProductionRateCap;
    public static float bacteriaMutationResistanceCap;
    public static long bacteriaColonySizeCap;
    public static int bacteriaLifespanCap;

    public static int bacteriaAnalyzerCraftingSpeed;
    public static int bacteriaAnalyzerPowerUsage;

    public static int mutatorCraftingSpeed;
    public static int mutatorPowerUsage;

    public static int abyssalEyesDepth;
    public static double photophoreSkinRadius;

    public static boolean biomeInjectionEnabled() {
        return !SPEC.isLoaded() || ENABLE_BIOME_INJECTION.getAsBoolean();
    }

    public static Set<String> injectableWorldPresets() {
        if (!SPEC.isLoaded()) {
            return Set.of("minecraft:overworld");
        }
        return Set.copyOf(INJECTABLE_WORLD_PRESETS.get());
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        kelpHeight = KELP_HEIGHT.get();
        spawnBookInInventory = SPAWN_BOOK_IN_INVENTORY.get();
        collectSaltWater = COLLECT_SALT_WATER.getAsBoolean();
        collectAirWithBottle = COLLECT_AIR_WITH_BOTTLE.getAsBoolean();

        mixerPower = MIXER_POWER_REQUIREMENT.get();
        drainPower = DRAIN_POWER_REQUIREMENT.get();
        augmentationStationPower = AUGMENTATION_STATION_POWER_REQUIREMENT.get();

        laserDistance = REGULAR_LASER_DISTANCE.get();
        longDistanceLaserDistance = LONG_DISTANCE_LASER_DISTANCE.get();

        mixerInputCapacity = MIXER_INPUT_CAPACITY.getAsInt();
        mixerOutputCapacity = MIXER_OUTPUT_CAPACITY.getAsInt();

        drainSaltWaterAmount = DRAIN_SALT_WATER_AMOUNT.getAsInt();
        drainCapacity = DRAIN_CAPACITY.getAsInt();

        guardianAugmentDamage = GUARDIAN_AUGMENT_DAMAGE.get();
        allowAugmentRendering = ALLOW_AUGMENT_RENDERING.get();

        fisherLaserLevel = FISHER_LASER_LEVEL.getAsInt();
        fisherRunDuration = FISHER_DURATION.getAsInt();
        fisherDepth = FISHER_DEPTH.getAsInt();
        fisherRadius = FISHER_RADIUS.getAsInt();

        bacteriaGrowthRateCap = (float) BACTERIA_GROWTH_RATE_CAP.getAsDouble();
        bacteriaProductionRateCap = (float) BACTERIA_PRODUCTION_RATE_CAP.getAsDouble();
        bacteriaMutationResistanceCap = (float) BACTERIA_MUTATION_RESISTANCE_CAP.getAsDouble();
        bacteriaColonySizeCap = BACTERIA_COLONY_SIZE_CAP.get();
        bacteriaLifespanCap = BACTERIA_LIFESPAN_CAP.get();

        bacteriaAnalyzerCraftingSpeed = BACTERIA_ANALYZER_CRAFTING_SPEED.get();
        bacteriaAnalyzerPowerUsage = BACTERIA_ANALYZER_POWER_USAGE.get();

        mutatorCraftingSpeed = MUTATOR_CRAFTING_SPEED.get();
        mutatorPowerUsage = MUTATOR_POWER_USAGE.get();

        abyssalEyesDepth = ABYSSAL_EYES_DEPTH.get();
        photophoreSkinRadius = PHOTOPHORE_SKIN_RADIUS.getAsDouble();
    }

}
