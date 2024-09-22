package com.portingdeadmods.modjam.client.renderer.robotArms;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.modjam.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.modjam.api.client.renderer.robotArms.RobotArmRenderer;
import com.portingdeadmods.modjam.client.model.block.RobotArmModel;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ClawRobotArmRenderer extends RobotArmRenderer {
    private final RobotArmModel model;

    public ClawRobotArmRenderer(AugmentRenderer.Context ctx) {
        super(ctx);
        this.model = new RobotArmModel(ctx.entityModelSet().bakeLayer(RobotArmModel.LAYER_LOCATION));
    }

    @Override
    public void render(AugmentationStationExtensionBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        int light = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());

        Direction direction = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

        VertexConsumer consumer = RobotArmModel.ROBOT_ARM_LOCATION.buffer(bufferSource, RenderType::entitySolid);
        // Bottom
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees((direction == Direction.EAST || direction == Direction.WEST
                    ? direction.getCounterClockWise()
                    : direction.getClockWise()).toYRot()));
            poseStack.translate(-0.5, 0, -0.5);
            renderArmBottom(poseStack, packedOverlay, consumer, light);
            // Middle
            poseStack.pushPose();
            {
                renderArmMiddle(poseStack, packedOverlay, consumer, light, blockEntity.getMiddleIndependentAngle(partialTick));
                // Tip
                poseStack.pushPose();
                {
                    renderArmTip(poseStack, packedOverlay, consumer, light, blockEntity.getTipIndependentAngle(partialTick));
                }
                poseStack.popPose();
            }
            poseStack.popPose();
        }
        poseStack.popPose();

    }

    private void renderArmBottom(PoseStack poseStack, int packedOverlay, VertexConsumer consumer, int light) {
        poseStack.translate(0.5, 0.625, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(0));
        model.renderPart(RobotArmModel.RobotArmParts.BOTTOM, poseStack, consumer, light, packedOverlay);
    }

    private void renderArmMiddle(PoseStack poseStack, int packedOverlay, VertexConsumer consumer, int light, float rotation) {
        poseStack.translate(0, 0.625, 0);

        poseStack.translate(0, -1.625, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(25));
        poseStack.mulPose(Axis.ZP.rotation(rotation));
        poseStack.translate(0, 1.03125, 0);
        model.renderPart(RobotArmModel.RobotArmParts.MIDDLE, poseStack, consumer, light, packedOverlay);
    }

    private void renderArmTip(PoseStack poseStack, int packedOverlay, VertexConsumer consumer, int light, float rotation) {
        poseStack.translate(0, 0.375 + 0.125, 0);

        poseStack.translate(0, -3 - 0.125, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(80));
        poseStack.mulPose(Axis.ZN.rotation(rotation));
        poseStack.translate(0, 2.5 + 0.0625 + 0.125, 0);
        model.renderPart(RobotArmModel.RobotArmParts.TIP, poseStack, consumer, light, packedOverlay);
    }
}
