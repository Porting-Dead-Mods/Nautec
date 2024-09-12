package com.portingdeadmods.modjam.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.modjam.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.modjam.client.model.WhiskModel;
import com.portingdeadmods.modjam.content.blockentities.MixerBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class MixerBERenderer extends LaserBlockEntityRenderer<MixerBlockEntity> {
    private final WhiskModel model;

    public MixerBERenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new WhiskModel(ctx.bakeLayer(WhiskModel.LAYER_LOCATION));
    }

    @Override
    public void render(MixerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.render(blockEntity, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        this.model.setupAnim();
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotation(blockEntity.getIndependentAngle(partialTick)));
            poseStack.translate(-0.5, 0, -0.5);
            poseStack.translate(0.5, 1.425, 0.75);
            this.model.renderToBuffer(
                    poseStack,
                    WhiskModel.WHISK_LOCATION.buffer(bufferSource, RenderType::entityCutout),
                    getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos().above()),
                    packedOverlay
            );
        }
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(skyLight, blockLight);
    }
}
