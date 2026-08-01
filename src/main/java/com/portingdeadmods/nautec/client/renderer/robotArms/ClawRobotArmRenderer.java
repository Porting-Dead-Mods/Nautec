package com.portingdeadmods.nautec.client.renderer.robotArms;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.robotArms.RobotArmRenderState;
import com.portingdeadmods.nautec.api.client.renderer.robotArms.RobotArmRenderer;
import com.portingdeadmods.nautec.client.model.block.RobotArmModel;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ClawRobotArmRenderer extends RobotArmRenderer {
    private final RobotArmModel model;

    public ClawRobotArmRenderer(EntityModelSet ctx) {
        super(ctx);
        this.model = new RobotArmModel(ctx.bakeLayer(RobotArmModel.LAYER_LOCATION));
    }

    @Override
    public void extractRenderState(AugmentationStationExtensionBlockEntity blockEntity, RobotArmRenderState state, float partialTick) {
        state.partialTick = partialTick;
        state.facing = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        state.lightAbove = blockEntity.getLevel() != null
                ? LevelRenderer.getLightCoords(blockEntity.getLevel(), blockEntity.getBlockPos().above())
                : 15728880;
        state.middleAngle = blockEntity.getMiddleIndependentAngle(partialTick);
        state.prevMiddleAngle = blockEntity.getPrevMiddleIndependentAngle(partialTick);
        state.tipAngle = blockEntity.getTipIndependentAngle(partialTick);
        state.prevTipAngle = blockEntity.getPrevTipIndependentAngle(partialTick);

        ItemStack item = blockEntity.getItemStackHandler().getStackInSlot(0);
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(state.heldItem, item, ItemDisplayContext.NONE, blockEntity.getLevel(), null, 1);
    }

    @Override
    public void submit(RobotArmRenderState state, PoseStack poseStack, SubmitNodeCollector collector) {
        int light = state.lightAbove;
        Direction direction = state.facing;

        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees((direction == Direction.EAST || direction == Direction.WEST
                    ? direction.getCounterClockWise()
                    : direction.getClockWise()).toYRot()));
            poseStack.translate(-0.5, 0, -0.5);
            renderArmBottom(poseStack, collector, light);
            poseStack.pushPose();
            {
                renderArmMiddle(poseStack, collector, light, Mth.lerp(state.partialTick, state.prevMiddleAngle, state.middleAngle));
                poseStack.pushPose();
                {
                    renderArmTip(poseStack, collector, light, Mth.lerp(state.partialTick, state.prevTipAngle, state.tipAngle));
                    submitItem(state, poseStack, collector);
                }
                poseStack.popPose();
            }
            poseStack.popPose();
        }
        poseStack.popPose();

    }

    private static void submitItem(RobotArmRenderState state, PoseStack poseStack, SubmitNodeCollector collector) {
        if (!state.heldItem.isEmpty()) {
            poseStack.translate(0, -3, 0);
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
            state.heldItem.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }
    }

    private void renderArmBottom(PoseStack poseStack, SubmitNodeCollector collector, int light) {
        poseStack.translate(0.5, 0.625, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(0));
        model.submitPart(RobotArmModel.RobotArmParts.BOTTOM, poseStack, collector, light, OverlayTexture.NO_OVERLAY);
    }

    private void renderArmMiddle(PoseStack poseStack, SubmitNodeCollector collector, int light, float rotation) {
        poseStack.translate(0, 0.625, 0);

        poseStack.translate(0, -1.625, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(25));
        poseStack.mulPose(Axis.ZP.rotation(rotation));
        poseStack.translate(0, 1.03125, 0);
        model.submitPart(RobotArmModel.RobotArmParts.MIDDLE, poseStack, collector, light, OverlayTexture.NO_OVERLAY);
    }

    private void renderArmTip(PoseStack poseStack, SubmitNodeCollector collector, int light, float rotation) {
        poseStack.translate(0, 0.375 + 0.125, 0);

        poseStack.translate(0, -3 - 0.125, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(80));
        poseStack.mulPose(Axis.ZN.rotation(rotation));
        poseStack.translate(0, 2.5 + 0.0625 + 0.125, 0);
        model.submitPart(RobotArmModel.RobotArmParts.TIP, poseStack, collector, light, OverlayTexture.NO_OVERLAY);
    }
}
