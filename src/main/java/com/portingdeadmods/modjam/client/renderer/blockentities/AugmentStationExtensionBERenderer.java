package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.modjam.client.model.block.RobotArmModel;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class AugmentStationExtensionBERenderer implements BlockEntityRenderer<AugmentationStationExtensionBlockEntity> {
    private final RobotArmModel model;

    private int rotation;

    public AugmentStationExtensionBERenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new RobotArmModel(ctx.bakeLayer(RobotArmModel.LAYER_LOCATION));
    }

    @Override
    public void render(AugmentationStationExtensionBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (rotation < 359) {
            rotation++;
        } else {
            rotation = 0;
        }

        int light = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());

        if (blockEntity.hasRobotArm()) {
            VertexConsumer consumer = RobotArmModel.ROBOT_ARM_LOCATION.buffer(bufferSource, RenderType::entitySolid);
            // Bottom
            poseStack.pushPose();
            {

                renderArmBottom(poseStack, packedOverlay, consumer, light);
                // Middle
                poseStack.pushPose();
                {
                    renderArmMiddle(poseStack, packedOverlay, consumer, light);
                    // Tip
                    poseStack.pushPose();
                    {
                        renderArmTip(poseStack, packedOverlay, consumer, light);
                    }
                    poseStack.popPose();
                }
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }

    private void renderArmBottom(PoseStack poseStack, int packedOverlay, VertexConsumer consumer, int light) {
        poseStack.translate(0.5, 0.625, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        model.renderPart(RobotArmModel.RobotArmParts.BOTTOM, poseStack, consumer, light, packedOverlay);
    }

    private void renderArmMiddle(PoseStack poseStack, int packedOverlay, VertexConsumer consumer, int light) {
        poseStack.translate(0, 0.625, 0);

        poseStack.translate(0, -1.625, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.translate(0, 1.03125, 0);
        model.renderPart(RobotArmModel.RobotArmParts.MIDDLE, poseStack, consumer, light, packedOverlay);
    }

    private void renderArmTip(PoseStack poseStack, int packedOverlay, VertexConsumer consumer, int light) {
        poseStack.translate(0, 0.375 + 0.0625, 0);

        poseStack.translate(0, -2.375, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.translate(0, 1.875 + 0.0625, 0);
        model.renderPart(RobotArmModel.RobotArmParts.TIP, poseStack, consumer, light, packedOverlay);
    }
}
