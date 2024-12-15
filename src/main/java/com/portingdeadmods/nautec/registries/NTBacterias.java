package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.FastColor;

public final class NTBacterias {
    public static final ResourceKey<Bacteria> EMPTY = key("empty");

    public static void bootstrap(BootstrapContext<Bacteria> context) {
        register(context, EMPTY, Bacteria.of()
                .color(FastColor.ARGB32.color(255, 0, 255))
                .growthRate(0.1f)
                .lifespan(10)
                .productionRate(1.0f)
                .mutationResistance(2.0f));
    }

    private static void register(BootstrapContext<Bacteria> context, ResourceKey<Bacteria> key, Bacteria.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Bacteria> key(String name) {
        return ResourceKey.create(NTRegistries.BACTERIA_KEY, Nautec.rl(name));
    }
}
