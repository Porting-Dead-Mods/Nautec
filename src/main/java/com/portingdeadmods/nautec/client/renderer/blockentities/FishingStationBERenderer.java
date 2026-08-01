package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.client.model.block.FishingNetModel;
import com.portingdeadmods.nautec.content.blockentities.FishingStationBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Unit;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FishingStationBERenderer implements BlockEntityRenderer<FishingStationBlockEntity, FishingStationBERenderer.FishingStationRenderState> {
    private final FishingNetModel model;

    public FishingStationBERenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new FishingNetModel(ctx.bakeLayer(FishingNetModel.LAYER_LOCATION));
        this.model.setupAnim();
    }

    @Override
    public FishingStationRenderState createRenderState() {
        return new FishingStationRenderState();
    }

    @Override
    public void extractRenderState(FishingStationBlockEntity blockEntity, FishingStationRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderState.extractBase(blockEntity, state, crumbling);
        state.running = blockEntity.isRunning();
        state.angle = blockEntity.getIndependentAngle(partialTick);
    }

    @Override
    public void submit(FishingStationRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.translate(1.75, -0.125, 0);
            poseStack.pushPose();
            {
                if (state.running) {
                    poseStack.translate(-1.75, 0, 0);
                    poseStack.mulPose(Axis.YN.rotationDegrees(state.angle));
                    poseStack.translate(1.75, 0, 0);
                }
                collector.submitModel(this.model, Unit.INSTANCE, poseStack, FishingNetModel.RENDER_TYPE, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, state.breakProgress);
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    public static class FishingStationRenderState extends BlockEntityRenderState {
        public boolean running;
        public float angle;
    }
}
