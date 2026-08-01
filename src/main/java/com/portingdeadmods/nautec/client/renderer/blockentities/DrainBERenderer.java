package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserRenderState;
import com.portingdeadmods.nautec.client.model.block.DrainTopModel;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DrainBERenderer extends LaserBlockEntityRenderer<DrainBlockEntity, DrainBERenderer.DrainRenderState> {
    private final DrainTopModel model;

    public DrainBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new DrainTopModel(ctx.bakeLayer(DrainTopModel.LAYER_LOCATION));
        this.model.setupAnimation();
    }

    @Override
    public DrainRenderState createRenderState() {
        return new DrainRenderState();
    }

    @Override
    public void extractRenderState(DrainBlockEntity blockEntity, DrainRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        super.extractRenderState(blockEntity, state, partialTick, cameraPos, crumbling);
        state.formed = blockEntity.getBlockState().getValue(DrainMultiblock.FORMED);
        if (state.formed) {
            state.lidAngle = blockEntity.getLidIndependentAngle(partialTick);
            state.valveAngle = blockEntity.getValveIndependentAngle(partialTick);
            state.lightAbove = blockEntity.getLevel() != null
                    ? LevelRenderer.getLightCoords(blockEntity.getLevel(), blockEntity.getBlockPos().above())
                    : 15728880;
        }
    }

    @Override
    public void submit(DrainRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        super.submit(state, poseStack, collector, camera);
        if (state.formed) {
            poseStack.pushPose();
            {
                poseStack.translate(0, 1, 0);

                poseStack.translate(-0.75, 0, -0.75);
                poseStack.mulPose(Axis.YP.rotation(state.lidAngle));
                poseStack.translate(0.75, 0, 0.75);
                this.model.submitLid(poseStack, collector, state.lightAbove, OverlayTexture.NO_OVERLAY);

                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotation(state.valveAngle));
                poseStack.translate(-0.5, 0, -0.5);
                this.model.submitValve(poseStack, collector, state.lightAbove, OverlayTexture.NO_OVERLAY);
            }
            poseStack.popPose();
        }
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(DrainBlockEntity blockEntity) {
        return blockEntity.getBlockState().getValue(DrainMultiblock.DRAIN_PART) == 4
                ? new AABB(blockEntity.getBlockPos()).inflate(1)
                : super.getRenderBoundingBox(blockEntity);
    }

    public static class DrainRenderState extends LaserRenderState {
        public boolean formed;
        public float lidAngle;
        public float valveAngle;
        public int lightAbove;
    }
}
