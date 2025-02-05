package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.client.model.block.FishingNetModel;
import com.portingdeadmods.nautec.content.blockentities.FishingStationBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class FishingStationBERenderer implements BlockEntityRenderer<FishingStationBlockEntity> {
    private final FishingNetModel model;

    public FishingStationBERenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new FishingNetModel(ctx.bakeLayer(FishingNetModel.LAYER_LOCATION));
        this.model.setupAnim();
    }

    @Override
    public void render(FishingStationBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.translate(1.75, -0.125, 0);
            poseStack.pushPose();
            {
                if (blockEntity.isRunning()) {
                    poseStack.translate(-1.75, 0, 0);
                    poseStack.mulPose(Axis.YN.rotationDegrees(blockEntity.getIndependentAngle(partialTick)));
                    poseStack.translate(1.75, 0, 0);
                }
                this.model.renderToBuffer(poseStack, FishingNetModel.MATERIAL.buffer(bufferSource, RenderType::entityCutout), packedLight, packedOverlay);
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
