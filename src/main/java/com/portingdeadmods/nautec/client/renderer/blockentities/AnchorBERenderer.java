package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.client.model.block.AnchorModel;
import com.portingdeadmods.nautec.content.blockentities.AnchorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class AnchorBERenderer implements BlockEntityRenderer<AnchorBlockEntity> {
    private final AnchorModel model;

    public AnchorBERenderer(BlockEntityRendererProvider.Context context) {
        this.model = new AnchorModel(context.bakeLayer(AnchorModel.LAYER_LOCATION));
        model.setupAnim();
    }

    @Override
    public void render(AnchorBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        model.renderToBuffer(poseStack, AnchorModel.MATERIAL.buffer(bufferSource, RenderType::entitySolid), packedLight, packedOverlay);

    }
}
