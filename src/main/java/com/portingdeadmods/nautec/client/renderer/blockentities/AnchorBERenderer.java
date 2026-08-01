package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.client.model.block.AnchorModel;
import com.portingdeadmods.nautec.content.blockentities.AnchorBlockEntity;
import com.portingdeadmods.nautec.content.blocks.AnchorBlock;
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

public class AnchorBERenderer implements BlockEntityRenderer<AnchorBlockEntity, AnchorBERenderer.AnchorRenderState> {
    private final AnchorModel model;

    public AnchorBERenderer(BlockEntityRendererProvider.Context context) {
        this.model = new AnchorModel(context.bakeLayer(AnchorModel.LAYER_LOCATION));
        model.setupAnim();
    }

    @Override
    public AnchorRenderState createRenderState() {
        return new AnchorRenderState();
    }

    @Override
    public void extractRenderState(AnchorBlockEntity blockEntity, AnchorRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderState.extractBase(blockEntity, state, crumbling);
        state.yRot = blockEntity.getBlockState().getValue(AnchorBlock.FACING).toYRot();
    }

    @Override
    public void submit(AnchorRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
            collector.submitModel(this.model, Unit.INSTANCE, poseStack, AnchorModel.RENDER_TYPE, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, state.breakProgress);
        }
        poseStack.popPose();
    }

    public static class AnchorRenderState extends BlockEntityRenderState {
        public float yRot;
    }
}
