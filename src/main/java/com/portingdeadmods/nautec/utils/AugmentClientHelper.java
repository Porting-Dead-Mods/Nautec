package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public final class AugmentClientHelper {
    public static void initCache(Player player) {
        Map<AugmentSlot, Augment> playerAugments = AugmentHelper.getAugments(player);
        Map<AugmentSlot, Augment> filteredAugments = new HashMap<>();
        
        // Only include non-null augments in the cache
        for (Map.Entry<AugmentSlot, Augment> entry : playerAugments.entrySet()) {
            if (entry.getValue() != null) {
                filteredAugments.put(entry.getKey(), entry.getValue());
            }
        }
        
        AugmentLayerRenderer.AUGMENTS_CACHE = filteredAugments;
    }

    public static void invalidateCacheFor(Player player, AugmentSlot augmentSlot) {
        AugmentLayerRenderer.AUGMENTS_CACHE.remove(augmentSlot);
        // Only put augment back in cache if it's not null
        var augment = AugmentHelper.getAugmentBySlot(player, augmentSlot);
        if (augment != null) {
            AugmentLayerRenderer.AUGMENTS_CACHE.put(augmentSlot, augment);
        }
    }
}
