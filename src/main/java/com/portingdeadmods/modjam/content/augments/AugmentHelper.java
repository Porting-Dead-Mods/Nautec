package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.network.SetAugmentDataPayload;
import com.portingdeadmods.modjam.registries.MJDataAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class AugmentHelper {
    private static final HashMap<Integer, StaticAugment> augmentHashMap = new HashMap<>();

    public static void AddAugment(StaticAugment augment, int id) {
        augmentHashMap.put(id, augment);
    }

    public static StaticAugment getAugment(Player player, Slot slot) {
        int id = getId(player, slot);
        return augmentHashMap.get(id);
    }

    public static Supplier<AttachmentType<Integer>> getAttachment(Slot slot) {
        return switch (slot) {
            case HEAD -> MJDataAttachments.HEAD_AUGMENTATION;
            case BODY -> MJDataAttachments.BODY_AUGMENTATION;
            case LEGS -> MJDataAttachments.LEGS_AUGMENTATION;
            case ARMS -> MJDataAttachments.ARMS_AUGMENTATION;
            case HEART -> MJDataAttachments.HEART_AUGMENTATION;
            case NONE -> null;
        };
    }

    public static int getId(Player player, Slot slot) {
        return player.getData(getAttachment(slot).get());
        // return -2;
    }

    public static void setId(Player player, Slot slot, int id) {
        player.setData(getAttachment(slot).get(), id);
    }

    public static void setIdAndUpdate(Player player, Slot slot, int id) {
        if (player.level().isClientSide()) {
            PacketDistributor.sendToServer(new SetAugmentDataPayload(id, slot.slotId));
        } else {
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SetAugmentDataPayload(id, slot.slotId));
        }
        setId(player, slot, id);
    }

    public static void incId(Player player, Slot slot) {
        setIdAndUpdate(player, slot, AugmentHelper.getId(player, slot) + 1);
        player.sendSystemMessage(Component.literal("Incremented to Id " + getId(player, slot) + " for slot " + slot.name()));

    }

    public static void decId(Player player, Slot slot) {
        setIdAndUpdate(player, slot, AugmentHelper.getId(player, slot) - 1);
        player.sendSystemMessage(Component.literal("Decremented to Id " + getId(player, slot) + " for slot " + slot.name()));
    }

    public static StaticAugment getAugment(int id) {
        return augmentHashMap.get(id);
    }

    public static StaticAugment[] getAugments(Player player) {
        List<StaticAugment> augments = new ArrayList<>();
        augments.add(getAugment(player, Slot.HEAD));
        augments.add(getAugment(player, Slot.BODY));
        augments.add(getAugment(player, Slot.ARMS));
        augments.add(getAugment(player, Slot.LEGS));
        augments.add(getAugment(player, Slot.HEART));

        return augments.toArray(new StaticAugment[10]);
    }
}
