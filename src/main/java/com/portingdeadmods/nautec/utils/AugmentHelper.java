package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.data.MJDataAttachments;
import net.minecraft.nbt.CompoundTag;
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

    public static Map<AugmentSlot, CompoundTag> getAugmentsData(Player player) {
        return player.getData(MJDataAttachments.AUGMENTS_EXTRA_DATA);
    }

    public static void setAugment(Player player, AugmentSlot augmentSlot, Augment augment) {
        Map<AugmentSlot, Augment> augments = new HashMap<>(getAugments(player));
        augments.put(augmentSlot, augment);
        player.setData(MJDataAttachments.AUGMENTS, augments);
    }

    public static void setAugmentExtraData(Player player, AugmentSlot augmentSlot, CompoundTag tag) {
        Map<AugmentSlot, CompoundTag> augments = new HashMap<>(getAugmentsData(player));
        augments.put(augmentSlot, tag);
        player.setData(MJDataAttachments.AUGMENTS_EXTRA_DATA, augments);
    }
}
