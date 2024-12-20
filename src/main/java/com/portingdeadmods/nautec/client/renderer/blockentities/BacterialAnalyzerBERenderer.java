package com.portingdeadmods.nautec.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.NTBERenderer;
import com.portingdeadmods.nautec.content.blockentities.BacterialAnalyzerBlockEntity;
import com.portingdeadmods.nautec.content.blocks.BacterialAnalyzerBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BacterialAnalyzerBERenderer extends NTBERenderer<BacterialAnalyzerBlockEntity> {
    public BacterialAnalyzerBERenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BacterialAnalyzerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack stack = blockEntity.getItemHandler().getStackInSlot(0);

        poseStack.pushPose();
        {
            poseStack.translate(0.5, 1.14f, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockState().getValue(BacterialAnalyzerBlock.FACING).getOpposite().toYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.scale(0.35f, 0.35f, 0.35f);
            context.getItemRenderer()
                    .renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 42);
        }
        poseStack.popPose();
    }
}
