package com.portingdeadmods.nautec.api.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.nautec.client.model.block.AnchorModel;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AnchorItemRenderer extends BlockEntityWithoutLevelRenderer {
    private AnchorModel model;

    public AnchorItemRenderer() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (model == null) {
            EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
            this.model = new AnchorModel(entityModels.bakeLayer(AnchorModel.LAYER_LOCATION));
            this.model.setupAnim();
        }
        VertexConsumer consumer = AnchorModel.MATERIAL.buffer(buffer, RenderType::entitySolid);
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay);
    }
}
