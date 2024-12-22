package com.portingdeadmods.nautec.api.client.renderer.robotArms;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;

public abstract class RobotArmRenderer {
    public RobotArmRenderer(EntityModelSet ctx) {
    }

    public abstract void render(AugmentationStationExtensionBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay);
}
