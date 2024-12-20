package com.portingdeadmods.nautec.api.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class NTBERenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    protected final BlockEntityRendererProvider.Context context;

    public NTBERenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

    }
}
