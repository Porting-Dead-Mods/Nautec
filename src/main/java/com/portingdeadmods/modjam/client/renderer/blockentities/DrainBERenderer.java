package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.portingdeadmods.modjam.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.client.model.block.DrainTopModel;
import com.portingdeadmods.modjam.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.modjam.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.modjam.content.multiblocks.DrainMultiblock;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class DrainBERenderer extends LaserBlockEntityRenderer<DrainPartBlockEntity> {
    private final DrainTopModel model;

    public DrainBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new DrainTopModel(ctx.bakeLayer(DrainTopModel.LAYER_LOCATION));
    }

    @Override
    public void render(DrainPartBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        if (blockState.getValue(DrainMultiblock.DRAIN_PART) == 4 && blockState.getValue(DrainPartBlock.TOP)) {
            DrainBlockEntity drainBE = (DrainBlockEntity) blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos().below());
            VertexConsumer consumer = DrainTopModel.DRAIN_TOP_LOCATION.buffer(bufferSource, RenderType::entityTranslucent);
            this.model.setupAnimation();
            poseStack.pushPose();
            {
                float lidAngle = drainBE.getLidIndependentAngle(partialTick);

                poseStack.translate(-0.75, 0, -0.75);
                poseStack.mulPose(Axis.YP.rotation(lidAngle));
                poseStack.translate(0.75, 0, 0.75);
                this.model.renderLid(poseStack, consumer, packedLight, packedOverlay);
                poseStack.pushPose();
                {
                    float valveAngle = drainBE.getValveIndependentAngle(partialTick);

                    poseStack.translate(0.5, 0, 0.5);
                    poseStack.mulPose(Axis.YP.rotation(valveAngle));
                    poseStack.translate(-0.5, 0, -0.5);
                    this.model.renderValve(poseStack, consumer, packedLight, packedOverlay);
                }
                poseStack.popPose();
            }
            poseStack.popPose();
        } else {
            super.render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        }
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(DrainPartBlockEntity blockEntity) {
        return blockEntity.getBlockState().getValue(DrainMultiblock.DRAIN_PART) == 4
                ? new AABB(blockEntity.getBlockPos()).inflate(1)
                : super.getRenderBoundingBox(blockEntity);
    }
}
