package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.NTBERenderer;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.nautec.content.blockentities.DecorativePrismarineCrystalBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class DecorativePrismarineCrystalBERenderer extends NTBERenderer<DecorativePrismarineCrystalBlockEntity> {
    private final PrismarineCrystalModel model;

    public DecorativePrismarineCrystalBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new PrismarineCrystalModel(ctx.bakeLayer(PrismarineCrystalModel.LAYER_LOCATION));
        model.setupAnim();
    }

    @Override
    public void render(DecorativePrismarineCrystalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        VertexConsumer consumer = PrismarineCrystalModel.PRISMARINE_CRYSTAL_LOCATION.buffer(bufferSource, RenderType::entitySolid);
        poseStack.pushPose();
        {
            poseStack.translate(0, -2, 0);
            model.render(poseStack, consumer, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above()), packedOverlay);
        }
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        return LevelRenderer.getLightColor(level, pos);
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(DecorativePrismarineCrystalBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos().below(3)).expandTowards(0, 6, 0);
    }
}