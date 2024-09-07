package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.portingdeadmods.modjam.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.modjam.content.blockentities.AquaticCatalystBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class AquaticCatalystBERenderer extends LaserBlockEntityRenderer<AquaticCatalystBlockEntity> {
    public AquaticCatalystBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

}
