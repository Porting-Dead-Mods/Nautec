package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserRenderState;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.nautec.content.blockentities.multiblock.semi.PrismarineCrystalBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrismarineCrystalBERenderer extends LaserBlockEntityRenderer<PrismarineCrystalBlockEntity, PrismarineCrystalBERenderer.PrismarineCrystalRenderState> {
    private final PrismarineCrystalModel model;

    public PrismarineCrystalBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new PrismarineCrystalModel(ctx.bakeLayer(PrismarineCrystalModel.LAYER_LOCATION));
        model.setupAnim();
    }

    @Override
    public PrismarineCrystalRenderState createRenderState() {
        return new PrismarineCrystalRenderState();
    }

    @Override
    public void extractRenderState(PrismarineCrystalBlockEntity blockEntity, PrismarineCrystalRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        super.extractRenderState(blockEntity, state, partialTick, cameraPos, crumbling);
        state.breaking = blockEntity.isBreaking();
        state.breakingProgress = 0;
        if (state.breaking && blockEntity.getLevel() != null) {
            state.breakingProgress = ((float) (blockEntity.getLevel().getGameTime() - blockEntity.getStartTick()) + partialTick) / (float) blockEntity.getDuration();
        }
        state.lightAbove = blockEntity.getLevel() != null
                ? LevelRenderer.getLightCoords(blockEntity.getLevel(), blockEntity.getBlockPos().above())
                : 15728880;
    }

    @Override
    public void submit(PrismarineCrystalRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        super.submit(state, poseStack, collector, camera);
        poseStack.pushPose();
        {
            poseStack.translate(0, -2, 0);

            if (state.breaking) {
                float f = state.breakingProgress;
                if (f >= 0.0F && f <= 1.0F) {
                    float f2 = f * 6.2831855F;
                    float f3 = -1.5F * (Mth.cos(f2) + 0.5F) * Mth.sin(f2 / 2.0F);
                    poseStack.rotateAround(Axis.XP.rotation(f3 * 0.015625F), 0.5F, 0.0F, 0.5F);
                    float f4 = Mth.sin(f2);
                    poseStack.rotateAround(Axis.ZP.rotation(f4 * 0.015625F), 0.5F, 0.0F, 0.5F);
                }
            }
            model.submit(poseStack, collector, state.lightAbove, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(PrismarineCrystalBlockEntity blockEntity) {
        return blockEntity.shouldRender(Direction.UP)
                ? super.getRenderBoundingBox(blockEntity)
                : new AABB(blockEntity.getBlockPos().below(3)).expandTowards(0, 6, 0);
    }

    public static class PrismarineCrystalRenderState extends LaserRenderState {
        public boolean breaking;
        public float breakingProgress;
        public int lightAbove;
    }
}
