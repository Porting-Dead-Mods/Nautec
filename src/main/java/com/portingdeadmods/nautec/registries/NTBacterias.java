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

import java.util.List;

public final class NTBacterias {
    public static final ResourceKey<Bacteria> EMPTY = key("empty");

    // Primary
    public static final ResourceKey<Bacteria> CYANOBACTERIA = key("cyanobacteria");
    public static final ResourceKey<Bacteria> HALOBACTERIA = key("halobacteria");
    public static final ResourceKey<Bacteria> METHANOGENS = key("methanogens");
    public static final ResourceKey<Bacteria> THERMOPHILES = key("thermophiles");

    // Compounds
    public static final ResourceKey<Bacteria> PHOTOTROPHS = key("phototrophs");
    public static final ResourceKey<Bacteria> ACIDOPHILES = key("acidophiles");
    public static final ResourceKey<Bacteria> METALLOPHILES = key("metallophiles");
    public static final ResourceKey<Bacteria> LIGNOCYTES = key("lignocytes");
    public static final ResourceKey<Bacteria> CRYOBIONTS = key("cryobionts");
    public static final ResourceKey<Bacteria> SULFUROPHILES = key("sulfurophiles");
    public static final ResourceKey<Bacteria> SILICOPHILES = key("silicophiles");
    public static final ResourceKey<Bacteria> CALCIOPHILES = key("calciophiles");
    public static final ResourceKey<Bacteria> CRIMSON_MICROBES = key("crimson_microbes");
    public static final ResourceKey<Bacteria> WARPED_MICROBES = key("warped_microbes");
    public static final ResourceKey<Bacteria> RED_MYCOTROPHIC_BACTERIA = key("red_mycotrophic_bacteria");
    public static final ResourceKey<Bacteria> BROWN_MYCOTROPHIC_BACTERIA = key("brown_mycotrophic_bacteria");
    public static final ResourceKey<Bacteria> HALOTROPHS = key("halotrophs");
    public static final ResourceKey<Bacteria> CARNIVOROUS_BACTERIA = key("carnivorous_bacteria");
    public static final ResourceKey<Bacteria> BRYOPHYTOPHILES = key("bryophytophiles");
    public static final ResourceKey<Bacteria> ALGAEFORMERS = key("algaeformers");
    public static final ResourceKey<Bacteria> RHIZOBACTERIA = key("rhizobacteria");
    public static final ResourceKey<Bacteria> BOREOPHILES = key("boreophiles");
    public static final ResourceKey<Bacteria> JUNGLOPHILES = key("junglophiles");
    public static final ResourceKey<Bacteria> BAMBOOPHAGES = key("bamboophages");
    public static final ResourceKey<Bacteria> DARK_LIGNOCYTES = key("dark_lignocytes");
    public static final ResourceKey<Bacteria> ACACIOPHYLES = key("acaciophyles");
    public static final ResourceKey<Bacteria> CACTOPHYLES = key("cactophyles");
    public static final ResourceKey<Bacteria> CAROTOPHYLES = key("carotophyles");
    public static final ResourceKey<Bacteria> CUCURBITOPHILES = key("cucurbitophiles");
    public static final ResourceKey<Bacteria> BETA_PHYLOBACTERIA = key("beta_phylobacteria");
    public static final ResourceKey<Bacteria> MELOPHAGES = key("melophages");
    public static final ResourceKey<Bacteria> ADAMANTOPHILES = key("adamantophiles");
    public static final ResourceKey<Bacteria> SMARAGDOPHILES = key("smaragdophiles");
    public static final ResourceKey<Bacteria> AZURITOPHILES = key("azuritophiles");
    public static final ResourceKey<Bacteria> CARBOPHAGES = key("carbophages");

    public static void bootstrap(BootstrapContext<Bacteria> context) {
        register(context, EMPTY, EmptyBacteria.INSTANCE);
        register(context, CYANOBACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(320, 480))
                .resource(Items.IRON_INGOT)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(50, 255, 255)));
        register(context, HALOBACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(480, 560))
                .resource(Items.GOLD_INGOT)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(255, 229, 0)));
        register(context, METHANOGENS, SimpleBacteria.of()
                .initialSize(LongRange.of(240, 600))
                .resource(Items.COOKED_BEEF)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(235, 186, 237)));
        register(context, THERMOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(120, 230))
                .resource(Items.LAVA_BUCKET)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(255, 0, 0)));
        register(context, PHOTOTROPHS, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.SUGAR_CANE)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(34, 139, 34)));
        register(context, ACIDOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(180, 400))
                .resource(Items.REDSTONE)
                .productionRate(FloatRange.of(0.12F, 0.52F))
                .lifespan(IntRange.of(900, 2000))
                .growthRate(FloatRange.of(0.4F, 0.9F))
                .mutationResistance(FloatRange.of(0F, 0.2F))
                .color(FastColor.ARGB32.color(255, 0, 128)));
        register(context, METALLOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(260, 520))
                .resource(Items.COPPER_INGOT)
                .productionRate(FloatRange.of(0.18F, 0.58F))
                .lifespan(IntRange.of(1000, 2100))
                .growthRate(FloatRange.of(0.45F, 1.0F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(184, 115, 51)));
        register(context, LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(280, 490))
                .resource(Items.OAK_LOG)
                .productionRate(FloatRange.of(0.1F, 0.45F))
                .lifespan(IntRange.of(1300, 2500))
                .growthRate(FloatRange.of(0.5F, 1.0F))
                .mutationResistance(FloatRange.of(0F, 0.08F))
                .color(FastColor.ARGB32.color(139, 69, 19)));
        register(context, CRYOBIONTS, SimpleBacteria.of()
                .initialSize(LongRange.of(220, 420))
                .resource(Items.ICE)
                .productionRate(FloatRange.of(0.08F, 0.4F))
                .lifespan(IntRange.of(1400, 2600))
                .growthRate(FloatRange.of(0.4F, 0.9F))
                .mutationResistance(FloatRange.of(0F, 0.05F))
                .color(FastColor.ARGB32.color(173, 216, 230)));
        register(context, SULFUROPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(250, 530))
                .resource(Items.GUNPOWDER)
                .productionRate(FloatRange.of(0.14F, 0.54F))
                .lifespan(IntRange.of(900, 2000))
                .growthRate(FloatRange.of(0.55F, 1.2F))
                .mutationResistance(FloatRange.of(0F, 0.15F))
                .color(FastColor.ARGB32.color(255, 215, 0)));
        register(context, SILICOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(280, 520))
                .resource(Items.SAND)
                .productionRate(FloatRange.of(0.1F, 0.5F))
                .lifespan(IntRange.of(1200, 2400))
                .growthRate(FloatRange.of(0.5F, 1F))
                .mutationResistance(FloatRange.of(0F, 0.1F))
                .color(FastColor.ARGB32.color(210, 180, 140)));
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
                .color(FastColor.ARGB32.color(220, 20, 60)));
        register(context, WARPED_MICROBES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.WARPED_FUNGUS)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 0, 255)));
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
                .color(FastColor.ARGB32.color(0, 255, 255)));
        register(context, CARNIVOROUS_BACTERIA, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.ROTTEN_FLESH)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(15, 13, 2)));
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
                .color(FastColor.ARGB32.color(40, 255, 0)));
        register(context, BOREOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.SPRUCE_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(139, 69, 19)));
        register(context, JUNGLOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.JUNGLE_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 100, 0)));
        register(context, BAMBOOPHAGES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.BAMBOO)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 100, 0)));
        register(context, DARK_LIGNOCYTES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.DARK_OAK_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(139, 69, 19)));
        register(context, ACACIOPHYLES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.ACACIA_LOG)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(139, 69, 19)));
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
                .color(FastColor.ARGB32.color(255, 165, 0)));
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
        register(context, ADAMANTOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.DIAMOND)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 255, 255)));
        register(context, SMARAGDOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.EMERALD)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 255, 0)));
        register(context, AZURITOPHILES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.LAPIS_LAZULI)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 0, 255)));
        register(context, CARBOPHAGES, SimpleBacteria.of()
                .initialSize(LongRange.of(300, 500))
                .resource(Items.COAL)
                .productionRate(FloatRange.of(0.15F, 0.55F))
                .lifespan(IntRange.of(1100, 2300))
                .growthRate(FloatRange.of(0.5F, 1.1F))
                .mutationResistance(FloatRange.of(0F, 0.12F))
                .color(FastColor.ARGB32.color(0, 0, 0)));
    }

    private static void register(BootstrapContext<Bacteria> context, ResourceKey<Bacteria> key, Bacteria.Builder<?> builder) {
        context.register(key, builder.build());
    }

    private static ResourceKey<Bacteria> key(String name) {
        return ResourceKey.create(NTRegistries.BACTERIA_KEY, Nautec.rl(name));
    }
}
