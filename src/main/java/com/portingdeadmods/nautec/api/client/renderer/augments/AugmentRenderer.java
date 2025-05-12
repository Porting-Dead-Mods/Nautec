package com.portingdeadmods.nautec.api.client.renderer.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;

public abstract class AugmentRenderer<T extends Augment> {
    public AugmentRenderer(Context ctx) {
    }

    public abstract <E extends LivingEntity, M extends EntityModel<E>> void render(T augment, AugmentLayerRenderer<E, M> superRenderer, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight);

    public record Context(EntityModelSet entityModelSet) {
    }
}
