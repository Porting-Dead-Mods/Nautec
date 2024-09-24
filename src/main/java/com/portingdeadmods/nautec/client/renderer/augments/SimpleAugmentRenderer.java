package com.portingdeadmods.nautec.client.renderer.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.client.model.augments.AugmentModel;
import com.portingdeadmods.nautec.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;

import java.util.function.Function;

public class SimpleAugmentRenderer<T extends Augment> extends AugmentRenderer<T> {
    private final AugmentModel<T> model;
    private final Material material;
    private final boolean moveWithBody;

    public SimpleAugmentRenderer(Function<ModelPart, AugmentModel<T>> model, ModelLayerLocation layerLocation, Material material, boolean moveWithBody, Context ctx) {
        super(ctx);
        this.material = material;
        this.model = model.apply(ctx.entityModelSet().bakeLayer(layerLocation));
        this.moveWithBody = moveWithBody;
    }

    @Override
    public void render(T augment, AugmentLayerRenderer superRenderer, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        {
            ModelPart modelPart = augment.getAugmentSlot().getModelPart().getModelPart(superRenderer.getParentModel());
            if (modelPart != null && moveWithBody) {
                modelPart.translateAndRotate(poseStack);
            }
            this.model.renderToBuffer(poseStack, material.buffer(bufferSource, model::renderType), packedLight, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }
}
