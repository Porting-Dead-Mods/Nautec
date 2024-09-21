package com.portingdeadmods.modjam.api.client.renderer.entities;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;

@FunctionalInterface
public interface PartVisible {
    void setPartVisible(PlayerRenderer renderer, boolean visible);
}
