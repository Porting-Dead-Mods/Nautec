package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.network.ClearAugmentPayload;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

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
        if (player.level().isClientSide()) {
            AugmentClientHelper.invalidateCacheFor(player, augmentSlot);
        }
        return augment;
    }

    public static void removeAugment(Player player, AugmentSlot augmentSlot) {
        Augment augment = getAugmentBySlot(player, augmentSlot);
        if (augment != null) {
            augment.onRemoved(player);
        }
        Map<AugmentSlot, Augment> augments = new HashMap<>(getAugments(player));
        Map<AugmentSlot, CompoundTag> augmentsData = new HashMap<>(getAugmentsData(player));
        augments.remove(augmentSlot);
        augmentsData.remove(augmentSlot);
        player.setData(NTDataAttachments.AUGMENTS, augments);
        player.setData(NTDataAttachments.AUGMENTS_EXTRA_DATA, augmentsData);
        
        // Sync to client if on server
        if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new ClearAugmentPayload(augmentSlot));
        }
        
        // Invalidate client cache if on client
        if (player.level().isClientSide()) {
            AugmentClientHelper.invalidateCacheFor(player, augmentSlot);
        }
    }
}
