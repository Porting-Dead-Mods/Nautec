package com.portingdeadmods.nautec.client.renderer.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.client.model.augments.AugmentModel;
import com.portingdeadmods.nautec.api.client.renderer.augments.AugmentRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;

import java.util.function.Function;

public class SimpleAugmentRenderer<T extends Augment> extends AugmentRenderer<T> {
    private final AugmentModel<T> model;
    private final Material material;

    public SimpleAugmentRenderer(Function<ModelPart, AugmentModel<T>> model, ModelLayerLocation layerLocation, Material material, Context ctx) {
        super(ctx);
        this.material = material;
        this.model = model.apply(ctx.entityModelSet().bakeLayer(layerLocation));
    }

    @Override
    public void render(T augment, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.model.renderToBuffer(poseStack, material.buffer(bufferSource, model::renderType), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
