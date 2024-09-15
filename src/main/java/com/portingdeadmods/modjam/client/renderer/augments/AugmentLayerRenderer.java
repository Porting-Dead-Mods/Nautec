package com.portingdeadmods.modjam.client.renderer.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.client.model.augment.AugmentModel;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import com.portingdeadmods.modjam.content.augments.StaticAugment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class AugmentLayerRenderer extends RenderLayer<Player, PlayerModel<Player>> {
    public AugmentLayerRenderer(RenderLayerParent renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Player player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        StaticAugment[] augments = getAugment(player);
        for (StaticAugment augment : augments){
            renderAugmentModel(poseStack, bufferSource, packedLight, player, augment.getId());
        }
    }

    private void renderAugmentModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Player player, int id) {
        AugmentModel model = new AugmentModel();
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "textures/entity/augment/test_texture.png");
        model.renderToBuffer(poseStack,bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture)),packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }

    private StaticAugment[] getAugment(Player player) {
        return AugmentHelper.getAugments(player);
    }
}
