package com.portingdeadmods.nautec.content.fluids;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.fluids.FluidTemplate;
import net.minecraft.resources.Identifier;

public enum FluidTemplates implements FluidTemplate {
    MOLTEN_METAL(Nautec.rl("fluid/molten_fluid_still"),
            Nautec.rl("fluid/molten_fluid_flow"),
            Nautec.rl("fluid/molten_fluid_overlay")),
    OIL(Nautec.rl("fluid/oil_fluid_still"),
            Nautec.rl("fluid/oil_fluid_flow"),
            Nautec.rl("fluid/oil_overlay")),
    EAS(modFluidTexture("eas_fluid"),
            modFluidTexture("eas_fluid"),
            Nautec.rl("misc/in_water")),
    ETCHING_ACID(modFluidTexture("etching_acid"),
            modFluidTexture("etching_acid"),
            Nautec.rl("misc/in_water")),
    WATER(Identifier.parse("block/water_still"),
            Identifier.parse("block/water_flow"),
            Nautec.rl("misc/in_soap_water"));

    private final Identifier still;
    private final Identifier flowing;
    private final Identifier overlay;

    FluidTemplates(Identifier still, Identifier flowing, Identifier overlay) {
        this.still = still;
        this.flowing = flowing;
        this.overlay = overlay;
    }

    @Override
    public Identifier getStillTexture() {
        return still;
    }

    @Override
    public Identifier getFlowingTexture() {
        return flowing;
    }

    @Override
    public Identifier getOverlayTexture() {
        return overlay;
    }

    private static Identifier modFluidTexture(String name) {
        return Nautec.rl("fluid/" + name);
    }
}