package com.portingdeadmods.modjam.api.client.model.augments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.portingdeadmods.modjam.api.augments.Augment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public abstract class AugmentModel<T extends Augment> extends EntityModel<Player> {
    public AugmentModel(ModelPart root, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
    }
}
