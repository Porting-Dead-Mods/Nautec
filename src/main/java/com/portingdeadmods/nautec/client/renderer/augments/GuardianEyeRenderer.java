package com.portingdeadmods.nautec.client.renderer.augments;

import com.mojang.blaze3d.vertex.*;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.nautec.client.model.augment.GuardianEyeModel;
import com.portingdeadmods.nautec.client.renderer.augments.helper.GuardianEyeRenderHelper;
import com.portingdeadmods.nautec.content.augments.GuardianEyeAugment;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import com.portingdeadmods.nautec.utils.LaserRendererHelper;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class GuardianEyeRenderer extends AugmentRenderer<GuardianEyeAugment> {
    private final GuardianEyeModel model;

    public GuardianEyeRenderer(Context ctx) {
        super(ctx);
        this.model = new GuardianEyeModel(ctx.entityModelSet().bakeLayer(GuardianEyeModel.LAYER_LOCATION));
    }

    @Override
    public void render(GuardianEyeAugment augment, AugmentLayerRenderer superRenderer, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        {
            AugmentSlot augmentSlot = augment.getAugmentSlot();
            if (augmentSlot != null) {
                ModelPart modelPart = AugmentSlotsRenderer.modelPartBySlot(augmentSlot).getModelPart(superRenderer.getParentModel());
                if (modelPart != null) {
                    modelPart.translateAndRotate(poseStack);
                }
            }
            poseStack.translate(0, -20 / 16f, 0);
            this.model.renderToBuffer(poseStack, GuardianEyeModel.MATERIAL.buffer(bufferSource, model::renderType), packedLight, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }
}
