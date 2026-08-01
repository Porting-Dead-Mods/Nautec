package com.portingdeadmods.nautec.api.client.renderer.blockentities;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class NTBERenderer<T extends BlockEntity, S extends BlockEntityRenderState> implements BlockEntityRenderer<T, S> {
    protected final BlockEntityRendererProvider.Context context;

    public NTBERenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }
}
