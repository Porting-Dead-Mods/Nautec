package com.portingdeadmods.modjam.utils;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.data.MJDataAttachments;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public final class AugmentHelper {
    public static Augment getAugmentBySlot(Player player, AugmentSlot augmentSlot) {
        return getAugments(player).get(augmentSlot);
    }

    public static Map<AugmentSlot, Augment> getAugments(Player player) {
        return player.getData(MJDataAttachments.AUGMENTS);
    }

    public static void setAugment(Player player, AugmentSlot augmentSlot, Augment augment) {
        Map<AugmentSlot, Augment> augments = new HashMap<>(getAugments(player));
        augments.put(augmentSlot, augment);
        player.setData(MJDataAttachments.AUGMENTS, augments);
    }
}
