package com.portingdeadmods.nautec.api.worldgen;

import net.minecraft.world.level.biome.Climate;

public final class OceanClimates {
    public static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

    public static final Climate.Parameter DEEP_OCEAN_CONTINENTALNESS = Climate.Parameter.span(-1.05F, -0.455F);
    public static final Climate.Parameter OCEAN_CONTINENTALNESS = Climate.Parameter.span(-0.455F, -0.19F);

    public static final Climate.Parameter FROZEN_TEMPERATURE = Climate.Parameter.span(-1.0F, -0.45F);
    public static final Climate.Parameter COLD_TEMPERATURE = Climate.Parameter.span(-0.45F, -0.15F);
    public static final Climate.Parameter NORMAL_TEMPERATURE = Climate.Parameter.span(-0.15F, 0.2F);
    public static final Climate.Parameter LUKEWARM_TEMPERATURE = Climate.Parameter.span(0.2F, 0.55F);
    public static final Climate.Parameter WARM_TEMPERATURE = Climate.Parameter.span(0.55F, 1.0F);

    public static final Climate.Parameter SURFACE_DEPTH = Climate.Parameter.point(0.0F);
    public static final Climate.Parameter UNDERGROUND_DEPTH = Climate.Parameter.point(1.0F);

    private OceanClimates() {
    }
}
