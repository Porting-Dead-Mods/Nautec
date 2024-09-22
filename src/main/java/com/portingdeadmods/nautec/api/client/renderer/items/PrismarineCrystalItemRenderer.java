package com.portingdeadmods.nautec.api.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PrismarineCrystalItemRenderer extends BlockEntityWithoutLevelRenderer {
    public PrismarineCrystalItemRenderer() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
        PrismarineCrystalModel model = new PrismarineCrystalModel(entityModels.bakeLayer(PrismarineCrystalModel.LAYER_LOCATION));
        VertexConsumer consumer = PrismarineCrystalModel.PRISMARINE_CRYSTAL_LOCATION.buffer(buffer, RenderType::entitySolid);
        model.setupAnim();
        model.render(poseStack, consumer, packedLight, packedOverlay);
    }
}
