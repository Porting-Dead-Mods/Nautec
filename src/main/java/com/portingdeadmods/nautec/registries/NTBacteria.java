package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class NTBacteria {
    public static final ResourceKey<Bacteria> EMPTY = key("empty");

    public static void bootstrap(BootstrapContext<Bacteria> context) {
        register(context, EMPTY, new Bacteria.Builder());
    }

    private static void register(BootstrapContext<Bacteria> context, ResourceKey<Bacteria> key, Bacteria.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Bacteria> key(String name) {
        return ResourceKey.create(NTRegistries.BACTERIA_KEY, Nautec.rl(name));
    }
}
