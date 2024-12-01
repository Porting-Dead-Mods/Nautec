package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class AugmentClientHelper {
    public static void initCache(Player player) {
        AugmentLayerRenderer.AUGMENTS_CACHE = new HashMap<>(AugmentHelper.getAugments(player));
    }

    public static void invalidateCacheFor(Player player, AugmentSlot augmentSlot) {
        AugmentLayerRenderer.AUGMENTS_CACHE.remove(augmentSlot);
        AugmentLayerRenderer.AUGMENTS_CACHE.put(augmentSlot, AugmentHelper.getAugmentBySlot(player, augmentSlot));
    }
}
