package com.portingdeadmods.nautec.api.client.renderer.augments;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ModelPartGetter {
    @Nullable
    ModelPart getModelPart(PlayerModel<AbstractClientPlayer> renderer);
}
