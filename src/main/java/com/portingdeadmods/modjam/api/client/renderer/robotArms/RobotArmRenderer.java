package com.portingdeadmods.modjam.api.client.renderer.robotArms;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.modjam.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;

public abstract class RobotArmRenderer {
    public RobotArmRenderer(AugmentRenderer.Context ctx) {
    }

    public abstract void render(AugmentationStationExtensionBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);
}
