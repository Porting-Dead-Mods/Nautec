package com.portingdeadmods.nautec.compat.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.nautec.content.items.tiers.NTArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;

public class PrismMonocleCurioRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack,
                                                                          SlotContext slotContext,
                                                                          PoseStack poseStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource multiBufferSource,
                                                                          int light, float v, float v1, float v2, float v3, float v4, float v5) {
        LivingEntity entity = slotContext.entity();
        Minecraft mc = Minecraft.getInstance();
        EntityModelSet entityModels = mc.getEntityModels();
        ModelPart part = entityModels.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR);
        HumanoidModel<LivingEntity> playerHumanoidModel = new HumanoidModel<>(part);
        Model armorModel = ClientHooks.getArmorModel(entity, itemStack, EquipmentSlot.HEAD, playerHumanoidModel);
        List<ArmorMaterial.Layer> layers = NTArmorMaterials.PRISMARINE.value().layers();
        for (int i = 0; i < layers.size(); i++) {
            ArmorMaterial.Layer layer = layers.get(i);
            ResourceLocation armorTexture = ClientHooks.getArmorTexture(entity, itemStack, layer, false, EquipmentSlot.HEAD);
            IClientItemExtensions iClientItemExtensions = IClientItemExtensions.of(itemStack);
            int color = iClientItemExtensions.getArmorLayerTintColor(itemStack, entity, layer, i, iClientItemExtensions.getDefaultDyeColor(itemStack));
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.armorCutoutNoCull(armorTexture));
            ICurioRenderer.followBodyRotations(entity, playerHumanoidModel);
            armorModel.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, color);

        }
    }
}
