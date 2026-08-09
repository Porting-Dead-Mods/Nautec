package com.portingdeadmods.nautec.worldgen;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public final class NTBiomeKeys {
    public static final ResourceKey<Biome> ABYSSAL_TRENCH = key("abyssal_trench");
    public static final ResourceKey<Biome> BIOLUMINESCENT_GROVE = key("bioluminescent_grove");
    public static final ResourceKey<Biome> HYDROTHERMAL_VENTS = key("hydrothermal_vents");
    public static final ResourceKey<Biome> PRISMARINE_REEF = key("prismarine_reef");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, Nautec.rl(name));
    }

    private NTBiomeKeys() {
    }
}
