package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.bacteria.EmptyBacteria;
import com.portingdeadmods.nautec.content.bacteria.SimpleBacteria;
import com.portingdeadmods.nautec.utils.ranges.FloatRange;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import com.portingdeadmods.nautec.utils.ranges.LongRange;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public final class NTBacterias {
    public static final ArrayList<ResourceKey<Bacteria>> BACTERIAS;

    static {
        BACTERIAS = new ArrayList<>();
    }

    public static final ResourceKey<Bacteria> EMPTY = keyAndAddBacteria("empty");

    // Primary - Do not produce anything
    public static final ResourceKey<Bacteria> CYANOBACTERIA = keyAndAddBacteria("cyanobacteria");
    public static final ResourceKey<Bacteria> HALOBACTERIA = keyAndAddBacteria("halobacteria");
    public static final ResourceKey<Bacteria> METHANOGENS = keyAndAddBacteria("methanogens");
    public static final ResourceKey<Bacteria> THERMOPHILES = keyAndAddBacteria("thermophiles");

    // Wood
    public static final ResourceKey<Bacteria> LIGNOCYTES = keyAndAddBacteria("lignocytes");
    public static final ResourceKey<Bacteria> DARK_LIGNOCYTES = keyAndAddBacteria("dark_lignocytes");
    public static final ResourceKey<Bacteria> ACACIOPHYLES = keyAndAddBacteria("acaciophyles");
    public static final ResourceKey<Bacteria> JUNGLOPHILES = keyAndAddBacteria("junglophiles");
    public static final ResourceKey<Bacteria> BOREOPHILES = keyAndAddBacteria("boreophiles");
    public static final ResourceKey<Bacteria> BETULOPHILES = keyAndAddBacteria("betulophiles");
    public static final ResourceKey<Bacteria> CRIMSON_LIGNOCYTES = keyAndAddBacteria("crimson_lignocytes");
    public static final ResourceKey<Bacteria> WARPED_LIGNOCYTES = keyAndAddBacteria("warped_lignocytes");
    public static final ResourceKey<Bacteria> RHIZOPHORA_LIGNOCYTES = keyAndAddBacteria("rhizophora_lignocytes");
    public static final ResourceKey<Bacteria> PRUNUS_LIGNOCYTES = keyAndAddBacteria("prunus_lignocytes");


    // Ores and Minerals
    public static final ResourceKey<Bacteria> SILICOPHILES = keyAndAddBacteria("silicophiles");
    public static final ResourceKey<Bacteria> LITHOPHILES = keyAndAddBacteria("lithophiles");
    public static final ResourceKey<Bacteria> METALLOPHILES = keyAndAddBacteria("metallophiles");
    public static final ResourceKey<Bacteria> CALCIOPHILES = keyAndAddBacteria("calciophiles");
    public static final ResourceKey<Bacteria> ACIDOPHILES = keyAndAddBacteria("acidophiles");
    public static final ResourceKey<Bacteria> FERROPHILES = keyAndAddBacteria("ferrophiles");
    public static final ResourceKey<Bacteria> AURROPHILES = keyAndAddBacteria("aurrophiles");
    public static final ResourceKey<Bacteria> ADAMANTOPHILES = keyAndAddBacteria("adamantophiles");
    public static final ResourceKey<Bacteria> SMARAGDOPHILES = keyAndAddBacteria("smaragdophiles");
    public static final ResourceKey<Bacteria> AZURITOPHILES = keyAndAddBacteria("azuritophiles");
    public static final ResourceKey<Bacteria> CARBOPHAGES = keyAndAddBacteria("carbophages");

    // Plants
    public static final ResourceKey<Bacteria> PHOTOTROPHS = keyAndAddBacteria("phototrophs");
    public static final ResourceKey<Bacteria> CRIMSON_MICROBES = keyAndAddBacteria("crimson_microbes");
    public static final ResourceKey<Bacteria> WARPED_MICROBES = keyAndAddBacteria("warped_microbes");
    public static final ResourceKey<Bacteria> RED_MYCOTROPHIC_BACTERIA = keyAndAddBacteria("red_mycotrophic_bacteria");
    public static final ResourceKey<Bacteria> BROWN_MYCOTROPHIC_BACTERIA = keyAndAddBacteria("brown_mycotrophic_bacteria");
    public static final ResourceKey<Bacteria> HALOTROPHS = keyAndAddBacteria("halotrophs");
    public static final ResourceKey<Bacteria> BRYOPHYTOPHILES = keyAndAddBacteria("bryophytophiles");
    public static final ResourceKey<Bacteria> ALGAEFORMERS = keyAndAddBacteria("algaeformers");
    public static final ResourceKey<Bacteria> RHIZOBACTERIA = keyAndAddBacteria("rhizobacteria");
    public static final ResourceKey<Bacteria> BAMBOOPHAGES = keyAndAddBacteria("bamboophages");
    public static final ResourceKey<Bacteria> CACTOPHYLES = keyAndAddBacteria("cactophyles");
    public static final ResourceKey<Bacteria> CAROTOPHYLES = keyAndAddBacteria("carotophyles");
    public static final ResourceKey<Bacteria> CUCURBITOPHILES = keyAndAddBacteria("cucurbitophiles");
    public static final ResourceKey<Bacteria> BETA_PHYLOBACTERIA = keyAndAddBacteria("beta_phylobacteria");
    public static final ResourceKey<Bacteria> MELOPHAGES = keyAndAddBacteria("melophages");
    public static final ResourceKey<Bacteria> SOLANOPHILES = keyAndAddBacteria("solanophiles");
    public static final ResourceKey<Bacteria> COCOAPHILES = keyAndAddBacteria("cocoaphiles");

    // Misc
    public static final ResourceKey<Bacteria> SULFUROPHILES = keyAndAddBacteria("sulfurophiles");
    public static final ResourceKey<Bacteria> CRYOBIONTS = keyAndAddBacteria("cryobionts");
    public static final ResourceKey<Bacteria> CARNIVOROUS_BACTERIA = keyAndAddBacteria("carnivorous_bacteria");

    public static void bootstrap(BootstrapContext<Bacteria> context) {
        register(context, EMPTY, EmptyBacteria.INSTANCE);

        // Primary
        register(context, CYANOBACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(320, 480))
                .resource(Items.AIR)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(50, 255, 255)));
        register(context, HALOBACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(480, 560))
                .resource(Items.AIR)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(255, 229, 0)));
        register(context, THERMOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(120, 230))
                .resource(Items.AIR)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(255, 0, 0)));
        register(context, METHANOGENS, SimpleBacteria.of()
                .initialSize(LongRange.of(240, 600))
                .resource(Items.AIR)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(235, 186, 237)));

        // Plants
        register(context, PHOTOTROPHS, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.SUGAR_CANE)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(149, 242, 67)));
        register(context, CALCIOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(260, 500))
                .resource(Items.BONE_MEAL)
                .productionRate(FloatRange.of(0.12F, 0.48F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.45F, 1.0F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(245, 245, 220)));
        register(context, CRIMSON_MICROBES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.CRIMSON_FUNGUS)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(146, 24, 24)));
        register(context, WARPED_MICROBES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.WARPED_FUNGUS)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(20, 178, 131)));
        register(context, RED_MYCOTROPHIC_BACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.RED_MUSHROOM)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(255, 0, 0)));
        register(context, BROWN_MYCOTROPHIC_BACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.BROWN_MUSHROOM)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(139, 69, 19)));
        register(context, HALOTROPHS, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.KELP)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(88, 169, 47)));
        register(context, BRYOPHYTOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.MOSS_BLOCK)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 100, 0)));
        register(context, ALGAEFORMERS, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.SEAGRASS)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 255, 25)));
        register(context, RHIZOBACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.WHEAT)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(217, 185, 100)));
        register(context, SOLANOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.POTATO)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(223, 195, 90)));
        register(context, BAMBOOPHAGES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.BAMBOO)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(93, 136, 36)));
        register(context, CACTOPHYLES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.CACTUS)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(10, 240, 30)));
        register(context, CAROTOPHYLES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.CARROT)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(252, 140, 9)));
        register(context, CUCURBITOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.PUMPKIN)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(255, 165, 0)));
        register(context, BETA_PHYLOBACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.BEETROOT)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(255, 0, 0)));
        register(context, MELOPHAGES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.MELON_SLICE)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 255, 0)));
        register(context, COCOAPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.COCOA_BEANS)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(84, 60, 33)));


        // Wood
        register(context, ACACIOPHYLES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.ACACIA_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(171, 92, 49)));
        register(context, DARK_LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.DARK_OAK_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(78, 49, 24)));
        register(context, JUNGLOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.JUNGLE_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(168, 120, 83)));
        register(context, BOREOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.SPRUCE_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(121, 89, 51)));
        register(context, LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(280, 490))
                .resource(Items.OAK_LOG)
                .productionRate(FloatRange.of(0.1F, 0.45F))
                .lifespan(IntRange.of(1300, 2500))
                .growthRate(FloatRange.of(0.5F, 1.0F))
                .mutationResistance(FloatRange.of(0F, 0.08F))
                .color(FastColor.ARGB32.color(173, 141, 84)));
        register(context, BETULOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.BIRCH_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(198, 181, 121)));
        register(context, CRIMSON_LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.CRIMSON_STEM)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(132, 61, 89)));
        register(context, WARPED_LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.WARPED_STEM)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(39, 111, 102)));
        register(context, RHIZOPHORA_LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.MANGROVE_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(110, 41, 44)));
        register(context, PRUNUS_LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.CHERRY_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(227, 179, 171)));

        // Ores and minerals
        register(context, LITHOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(280, 520))
                .resource(Items.STONE)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(180, 180, 190)));
        register(context, SILICOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(280, 520))
                .resource(Items.SAND)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(210, 180, 140)));
        register(context, CARBOPHAGES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.COAL)
                .productionRate(FloatRange.of(0.20F, 0.7F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(45, 45, 45)));
        register(context, METALLOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(280, 480))
                .resource(Items.COPPER_INGOT)
                .productionRate(FloatRange.of(0.2F, 0.65F))
                .lifespan(IntRange.of(1000, 2100))
                .growthRate(FloatRange.of(0.45F, 1.0F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(184, 115, 51)));
        register(context, ACIDOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(250, 450))
                .resource(Items.REDSTONE)
                .productionRate(FloatRange.of(0.2F, 0.6F))
                .lifespan(IntRange.of(900, 2000))
                .growthRate(FloatRange.of(0.4F, 0.9F))
                .mutationResistance(FloatRange.of(0F, 0.2F))
                .color(FastColor.ARGB32.color(255, 0, 128)));
        register(context, AZURITOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(250, 450))
                .resource(Items.LAPIS_LAZULI)
                .productionRate(FloatRange.of(0.2F, 0.6F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 0, 255)));
        register(context, FERROPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(225, 425))
                .resource(Items.IRON_INGOT)
                .productionRate(FloatRange.of(0.18F, 0.58F))
                .lifespan(IntRange.of(1000, 2100))
                .growthRate(FloatRange.of(0.45F, 1.0F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(184, 115, 51)));
        register(context, AURROPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(200, 400))
                .resource(Items.GOLD_INGOT)
                .productionRate(FloatRange.of(0.15F, 0.5F))
                .lifespan(IntRange.of(1000, 2100))
                .growthRate(FloatRange.of(0.45F, 1.0F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(184, 115, 51)));
        register(context, ADAMANTOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(150, 275))
                .resource(Items.DIAMOND)
                .productionRate(FloatRange.of(0.1F, 0.4F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 255, 255)));
        register(context, SMARAGDOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(125, 225))
                .resource(Items.EMERALD)
                .productionRate(FloatRange.of(0.1F, 0.4F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 255, 0)));

        // Misc
        register(context, CARNIVOROUS_BACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.ROTTEN_FLESH)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(178, 67, 32)));
        register(context, SULFUROPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(250, 530))
                .resource(Items.GUNPOWDER)
                .productionRate(FloatRange.of(0.14F, 0.54F))
                .lifespan(IntRange.of(900, 2000))
                .growthRate(FloatRange.of(0.55F, 1.2F))
                .mutationResistance(FloatRange.of(0F, 0.15F))
                .color(FastColor.ARGB32.color(133, 133, 133)));
        register(context, CRYOBIONTS, SimpleBacteria.of()
                .initialSize(LongRange.of(220, 420))
                .resource(Items.ICE)
                .productionRate(FloatRange.of(0.08F, 0.4F))
                .lifespan(IntRange.of(1400, 2600))
                .growthRate(FloatRange.of(0.4F, 0.9F))
                .mutationResistance(FloatRange.of(0F, 0.05F))
                .color(FastColor.ARGB32.color(173, 216, 230)));
    }

    private static void register(BootstrapContext<Bacteria> context, ResourceKey<Bacteria> key, Bacteria.Builder<?> builder) {
        context.register(key, builder.build());
    }

    private static ResourceKey<Bacteria> key(String name) {
        return ResourceKey.create(NTRegistries.BACTERIA_KEY, Nautec.rl(name));
    }

    private static ResourceKey<Bacteria> keyAndAddBacteria(String name) {
        ResourceKey<Bacteria> key = key(name);

        BACTERIAS.add(key);
        return key;
    }
}
