package com.portingdeadmods.modjam.api.fluids;

import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;

public class BaseFluidType extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final Vec3i color;

    public BaseFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, Vec3i color, FluidType.Properties properties) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        this.color = color;
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }

    public Vec3i getColor() {
        return color;
    }
}