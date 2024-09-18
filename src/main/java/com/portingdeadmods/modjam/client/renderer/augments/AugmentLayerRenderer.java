package com.portingdeadmods.modjam.client.renderer.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.client.model.augment.AugmentModel;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Iterator;
import java.util.List;

public class AugmentLayerRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public AugmentLayerRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        Iterable<Augment> augments = getAugments(player);
        for (Augment augment : augments) {
            if (augment != null) {
                renderAugmentModel(poseStack, bufferSource, packedLight, augment);
            }
        }
    }

    private void renderAugmentModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Augment augment) {
        AugmentModel model = new AugmentModel();
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "textures/entity/augment/test_texture.png");
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture)), packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }

    private Iterable<Augment> getAugments(Player player) {
        return AugmentHelper.getAugments(player).values();
    }
}
