package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.modjam.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.modjam.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.modjam.content.blockentities.PrismarineCrystalBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

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
        poseStack.pushPose();
        {
            poseStack.translate(0, -2, 0);
            model.render(poseStack, consumer, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above()), packedOverlay);
        }
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(skyLight, blockLight);
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(PrismarineCrystalBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos().below(3)).expandTowards(0, 6, 0);
    }
}
