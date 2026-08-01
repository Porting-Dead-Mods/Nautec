package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.NTBERenderer;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.nautec.content.blockentities.DecorativePrismarineCrystalBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DecorativePrismarineCrystalBERenderer extends NTBERenderer<DecorativePrismarineCrystalBlockEntity, DecorativePrismarineCrystalBERenderer.DecorativeCrystalRenderState> {
    private final PrismarineCrystalModel model;

    public DecorativePrismarineCrystalBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new PrismarineCrystalModel(ctx.bakeLayer(PrismarineCrystalModel.LAYER_LOCATION));
        model.setupAnim();
    }

    @Override
    public DecorativeCrystalRenderState createRenderState() {
        return new DecorativeCrystalRenderState();
    }

    @Override
    public void extractRenderState(DecorativePrismarineCrystalBlockEntity blockEntity, DecorativeCrystalRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderState.extractBase(blockEntity, state, crumbling);
        state.lightAbove = blockEntity.getLevel() != null
                ? LevelRenderer.getLightCoords(blockEntity.getLevel(), blockEntity.getBlockPos().above())
                : 15728880;
    }

    @Override
    public void submit(DecorativeCrystalRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        {
            poseStack.translate(0, -2, 0);
            model.submit(poseStack, collector, state.lightAbove, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(DecorativePrismarineCrystalBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos().below(3)).expandTowards(0, 6, 0);
    }

    public static class DecorativeCrystalRenderState extends BlockEntityRenderState {
        public int lightAbove;
    }
}
