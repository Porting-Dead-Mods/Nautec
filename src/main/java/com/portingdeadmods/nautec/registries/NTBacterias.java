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
    public static final ResourceKey<Bacteria> CYANOBACTERIA = key("cyanobacteria");
    public static final ResourceKey<Bacteria> HALOBACTERIA = key("halobacteria");
    public static final ResourceKey<Bacteria> METHANOGENS = key("methanogens");
    public static final ResourceKey<Bacteria> THERMOPHILES = key("thermophiles");

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
    }

    private static void register(BootstrapContext<Bacteria> context, ResourceKey<Bacteria> key, Bacteria.Builder<?> builder) {
        context.register(key, builder.build());
    }

    private static ResourceKey<Bacteria> key(String name) {
        return ResourceKey.create(NTRegistries.BACTERIA_KEY, Nautec.rl(name));
    }
}
