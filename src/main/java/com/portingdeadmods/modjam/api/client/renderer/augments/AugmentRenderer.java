package com.portingdeadmods.modjam.api.client.renderer.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.modjam.api.augments.Augment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;

public abstract class AugmentRenderer<T extends Augment> {
    public AugmentRenderer(Context ctx) {
    }

    public abstract void render(T augment, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight);

    public record Context(EntityModelSet entityModelSet) {
    }
}
