package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.modjam.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.modjam.client.model.PrismarineCrystalModel;
import com.portingdeadmods.modjam.content.blockentities.PrismarineCrystalBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class PrismarineCrystalBERenderer extends LaserBlockEntityRenderer<PrismarineCrystalBlockEntity> {
    private final PrismarineCrystalModel model;

    public PrismarineCrystalBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new PrismarineCrystalModel(ctx.bakeLayer(PrismarineCrystalModel.LAYER_LOCATION));
    }

    @Override
    public void render(PrismarineCrystalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        VertexConsumer consumer = PrismarineCrystalModel.PRISMARINE_CRYSTAL_LOCATION.buffer(bufferSource, RenderType::entitySolid);
        model.setupAnim();
        model.renderToBuffer(poseStack, consumer, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above()), packedOverlay);
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(skyLight, blockLight);
    }
}
