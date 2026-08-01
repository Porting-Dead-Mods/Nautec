package com.portingdeadmods.nautec.api.client.renderer.robotArms;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;

public abstract class RobotArmRenderer {
    public RobotArmRenderer(EntityModelSet ctx) {
    }

    public void extractRenderState(AugmentationStationExtensionBlockEntity blockEntity, RobotArmRenderState state, float partialTick) {
    }

    public abstract void submit(RobotArmRenderState state, PoseStack poseStack, SubmitNodeCollector collector);
}
