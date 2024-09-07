package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.modjam.client.model.DrainTopModel;
import com.portingdeadmods.modjam.content.blockentities.multiblock.controller.DrainBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class DrainBERenderer implements BlockEntityRenderer<DrainBlockEntity> {
    private final DrainTopModel model;

    public DrainBERenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new DrainTopModel(ctx.bakeLayer(DrainTopModel.LAYER_LOCATION));
    }

    @Override
    public void render(DrainBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        VertexConsumer consumer = DrainTopModel.DRAIN_TOP_LOCATION.buffer(bufferSource, RenderType::entitySolid);
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay);
    }
}
