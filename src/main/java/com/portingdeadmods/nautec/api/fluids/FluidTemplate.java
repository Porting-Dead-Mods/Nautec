package com.portingdeadmods.nautec.api.fluids;

import net.minecraft.resources.ResourceLocation;

public interface FluidTemplate {
    ResourceLocation getStillTexture();

    ResourceLocation getFlowingTexture();

    ResourceLocation getOverlayTexture();
}
