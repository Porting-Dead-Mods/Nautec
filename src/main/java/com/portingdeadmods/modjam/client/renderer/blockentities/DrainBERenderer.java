package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.client.model.DrainTopModel;
import com.portingdeadmods.modjam.content.blockentities.multiblock.controller.DrainBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;

public class DrainBERenderer implements BlockEntityRenderer<DrainBlockEntity> {
    private final DrainTopModel model;

    public DrainBERenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new DrainTopModel(ctx.bakeLayer(DrainTopModel.LAYER_LOCATION));
    }

    @Override
    public void render(DrainBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getBlockState().getValue(Multiblock.FORMED)) {
            VertexConsumer consumer = DrainTopModel.DRAIN_TOP_LOCATION.buffer(bufferSource, RenderType::entityTranslucent);
            this.model.setupAnimation();
            this.model.renderToBuffer(poseStack, consumer, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above()), packedOverlay);
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(skyLight, blockLight);
    }

    @Override
    public boolean shouldRenderOffScreen(DrainBlockEntity blockEntity) {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox(DrainBlockEntity blockEntity) {
        return BlockEntityRenderer.super.getRenderBoundingBox(blockEntity).inflate(1);
    }
}