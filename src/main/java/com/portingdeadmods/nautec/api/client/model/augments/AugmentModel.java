package com.portingdeadmods.nautec.api.client.model.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import net.minecraft.client.model.EntityModel;
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
