package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.bacteria.SimpleBacteria;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Items;

public final class NTBacterias {
    public static final ResourceKey<Bacteria> EMPTY = key("empty");
    public static final ResourceKey<Bacteria> CYANOBACTERIA = key("cyanobacteria");
    public static final ResourceKey<Bacteria> HALOBACTERIA = key("halobacteria");
    public static final ResourceKey<Bacteria> METHANOGENS = key("methanogens");
    public static final ResourceKey<Bacteria> THERMOPHILES = key("thermophiles");

    public static void bootstrap(BootstrapContext<Bacteria> context) {
        register(context, EMPTY, SimpleBacteria.of());
        register(context, CYANOBACTERIA, SimpleBacteria.of()
                .resource(Items.IRON_INGOT)
                .color(FastColor.ARGB32.color(50, 255, 255)));
        register(context, HALOBACTERIA, SimpleBacteria.of()
                .resource(Items.GOLD_INGOT)
                .color(FastColor.ARGB32.color(255, 229, 0)));
        register(context, METHANOGENS, SimpleBacteria.of()
                .resource(Items.COOKED_BEEF)
                .color(FastColor.ARGB32.color(235, 186, 237)));
        register(context, THERMOPHILES, SimpleBacteria.of()
                .resource(Items.LAVA_BUCKET)
                .color(FastColor.ARGB32.color(255, 0, 0)));
    }

    private static void register(BootstrapContext<Bacteria> context, ResourceKey<Bacteria> key, SimpleBacteria.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Bacteria> key(String name) {
        return ResourceKey.create(NTRegistries.BACTERIA_KEY, Nautec.rl(name));
    }
}
