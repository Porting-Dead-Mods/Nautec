package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.NTBERenderer;
import com.portingdeadmods.nautec.content.blockentities.BacterialAnalyzerBlockEntity;
import com.portingdeadmods.nautec.content.blocks.BacterialAnalyzerBlock;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BacterialAnalyzerBERenderer extends NTBERenderer<BacterialAnalyzerBlockEntity, BacterialAnalyzerBERenderer.BacterialAnalyzerRenderState> {
    public BacterialAnalyzerBERenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public BacterialAnalyzerRenderState createRenderState() {
        return new BacterialAnalyzerRenderState();
    }

    @Override
    public void extractRenderState(BacterialAnalyzerBlockEntity blockEntity, BacterialAnalyzerRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderState.extractBase(blockEntity, state, crumbling);
        ItemStack stack = blockEntity.getItemStackHandler().getStackInSlot(0);
        state.yRot = blockEntity.getBlockState().getValue(BacterialAnalyzerBlock.FACING).getOpposite().toYRot();
        context.itemModelResolver().updateForTopItem(state.item, stack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 42);
    }

    @Override
    public void submit(BacterialAnalyzerRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 1.14f, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot));
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.scale(0.35f, 0.35f, 0.35f);
            state.item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }
        poseStack.popPose();
    }

    public static class BacterialAnalyzerRenderState extends BlockEntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
        public float yRot;
    }
}
