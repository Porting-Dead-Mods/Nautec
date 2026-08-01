package com.portingdeadmods.nautec.client.renderer.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.client.model.augments.AugmentModel;
import com.portingdeadmods.nautec.api.client.renderer.augments.AugmentRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;

import java.util.function.Function;

public class SimpleAugmentRenderer<T extends Augment> extends AugmentRenderer<T> {
    private final AugmentModel<T> model;
    private final RenderType renderType;
    private final boolean moveWithBody;

    public SimpleAugmentRenderer(Function<ModelPart, AugmentModel<T>> model, ModelLayerLocation layerLocation, RenderType renderType, boolean moveWithBody, Context ctx) {
        super(ctx);
        this.renderType = renderType;
        this.model = model.apply(ctx.entityModelSet().bakeLayer(layerLocation));
        this.moveWithBody = moveWithBody;
    }

    @Override
    public void render(T augment, AugmentLayerRenderer<?, ?> superRenderer, PoseStack poseStack, SubmitNodeCollector collector, int packedLight) {
        poseStack.pushPose();
        {
            AugmentSlot augmentSlot = augment.getAugmentSlot();
            if (augmentSlot != null) {
                ModelPart modelPart = AugmentSlotsRenderer.modelPartBySlot(augmentSlot).getModelPart((PlayerModel) superRenderer.getParentModel());
                if (modelPart != null && moveWithBody) {
                    modelPart.translateAndRotate(poseStack);
                }
            }
            this.model.submit(poseStack, collector, renderType, packedLight, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }
}
