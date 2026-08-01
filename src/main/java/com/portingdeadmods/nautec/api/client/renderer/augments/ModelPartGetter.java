package com.portingdeadmods.nautec.api.client.renderer.augments;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ModelPartGetter {
    @Nullable
    ModelPart getModelPart(PlayerModel renderer);
}
