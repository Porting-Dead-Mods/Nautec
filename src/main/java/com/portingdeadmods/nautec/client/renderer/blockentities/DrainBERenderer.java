package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.client.model.block.DrainTopModel;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class DrainBERenderer extends LaserBlockEntityRenderer<DrainBlockEntity> {
    private final DrainTopModel model;

    public DrainBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new DrainTopModel(ctx.bakeLayer(DrainTopModel.LAYER_LOCATION));
        this.model.setupAnimation();
    }

    @Override
    public void render(DrainBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getBlockState().getValue(DrainMultiblock.FORMED)) {
            VertexConsumer consumer = DrainTopModel.DRAIN_TOP_LOCATION.buffer(bufferSource, RenderType::entityTranslucent);
            poseStack.pushPose();
            {
                poseStack.translate(0, 1, 0);
                float lidAngle = blockEntity.getLidIndependentAngle(partialTick);
                int lightColor = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());

                poseStack.translate(-0.75, 0, -0.75);
                poseStack.mulPose(Axis.YP.rotation(lidAngle));
                poseStack.translate(0.75, 0, 0.75);
                this.model.renderLid(poseStack, consumer, lightColor, packedOverlay);
                float valveAngle = blockEntity.getValveIndependentAngle(partialTick);

                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotation(valveAngle));
                poseStack.translate(-0.5, 0, -0.5);
                this.model.renderValve(poseStack, consumer, lightColor, packedOverlay);
            }
            poseStack.popPose();
        }
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(DrainBlockEntity blockEntity) {
        return blockEntity.getBlockState().getValue(DrainMultiblock.DRAIN_PART) == 4
                ? new AABB(blockEntity.getBlockPos()).inflate(1)
                : super.getRenderBoundingBox(blockEntity);
    }
}
