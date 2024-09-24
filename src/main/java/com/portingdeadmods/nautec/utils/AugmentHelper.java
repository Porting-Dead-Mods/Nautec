package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public final class AugmentHelper {
    public static Augment getAugmentBySlot(Player player, AugmentSlot augmentSlot) {
        return getAugments(player).get(augmentSlot);
    }

    public static Map<AugmentSlot, Augment> getAugments(Player player) {
        return player.getData(NTDataAttachments.AUGMENTS);
    }

    public static Map<AugmentSlot, CompoundTag> getAugmentsData(Player player) {
        return player.getData(NTDataAttachments.AUGMENTS_EXTRA_DATA);
    }

    public static void setAugment(Player player, AugmentSlot augmentSlot, Augment augment) {
        Map<AugmentSlot, Augment> augments = new HashMap<>(getAugments(player));
        augments.put(augmentSlot, augment);
        player.setData(NTDataAttachments.AUGMENTS, augments);
    }

    public static void setAugmentExtraData(Player player, AugmentSlot augmentSlot, CompoundTag tag) {
        Map<AugmentSlot, CompoundTag> augments = new HashMap<>(getAugmentsData(player));
        augments.put(augmentSlot, tag);
        player.setData(NTDataAttachments.AUGMENTS_EXTRA_DATA, augments);
    }

    public static Augment createAugment(AugmentType<?> augmentType, Player player, AugmentSlot augmentSlot) {
        Augment augment = augmentType.create(augmentSlot);
        augment.setPlayer(player);
        augment.onAdded(player);
        AugmentHelper.setAugment(player, augmentSlot, augment);
        return augment;
    }

    public static void removeAugment(Player player, AugmentSlot augmentSlot) {
        Augment augment = getAugmentBySlot(player, augmentSlot);
        augment.onRemoved(player);
        Map<AugmentSlot, Augment> augments = new HashMap<>(getAugments(player));
        Map<AugmentSlot, CompoundTag> augmentsData = new HashMap<>(getAugmentsData(player));
        augments.remove(augmentSlot);
        augmentsData.remove(augmentSlot);
        player.setData(NTDataAttachments.AUGMENTS.get(), augments);
        player.setData(NTDataAttachments.AUGMENTS_EXTRA_DATA.get(), augmentsData);
    }
}
