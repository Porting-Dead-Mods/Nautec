package com.portingdeadmods.nautec.content.fluids;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.fluids.FluidTemplate;
import net.minecraft.resources.ResourceLocation;

public enum FluidTemplates implements FluidTemplate {
    MOLTEN_METAL(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/molten_fluid_still"),
            ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/molten_fluid_flow"),
            ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/molten_fluid_overlay")),
    OIL(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/oil_fluid_still"),
            ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/oil_fluid_flow"),
            ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/oil_overlay")),
    EAS(modFluidTexture("eas_fluid"),
            modFluidTexture("eas_fluid"),
            ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "misc/in_water")),
    ETCHING_ACID(modFluidTexture("etching_acid"),
            modFluidTexture("etching_acid"),
            ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "misc/in_water")),
    WATER(ResourceLocation.parse("block/water_still"),
            ResourceLocation.parse("block/water_flow"),
            ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "misc/in_soap_water"));

    private final ResourceLocation still;
    private final ResourceLocation flowing;
    private final ResourceLocation overlay;

    FluidTemplates(ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
        this.still = still;
        this.flowing = flowing;
        this.overlay = overlay;
    }

    @Override
    public ResourceLocation getStillTexture() {
        return still;
    }

    @Override
    public ResourceLocation getFlowingTexture() {
        return flowing;
    }

    @Override
    public ResourceLocation getOverlayTexture() {
        return overlay;
    }

    private static ResourceLocation modFluidTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "fluid/" + name);
    }
}