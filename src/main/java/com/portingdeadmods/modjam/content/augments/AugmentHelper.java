package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.network.SetAugmentDataPayload;
import com.portingdeadmods.modjam.network.SetCooldownPayload;
import com.portingdeadmods.modjam.registries.MJDataAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AugmentHelper {
    public static StaticAugment getAugment(Player player, Slot slot) {
        int id = getId(player, slot);
        return getAugment(id);
    }

    public static StaticAugment getAugment(int id) {
        return MJRegistries.AUGMENT.byId(id);
    }

    public static Supplier<AttachmentType<Integer>> getDataAttachment(Slot slot) {
        return switch (slot) {
            case HEAD -> MJDataAttachments.HEAD_AUGMENTATION_DATA;
            case BODY -> MJDataAttachments.BODY_AUGMENTATION_DATA;
            case LEGS -> MJDataAttachments.LEGS_AUGMENTATION_DATA;
            case ARMS -> MJDataAttachments.ARMS_AUGMENTATION_DATA;
            case HEART -> MJDataAttachments.HEART_AUGMENTATION_DATA;
            case NONE -> null;
        };
    }

    public static Supplier<AttachmentType<Integer>> getCooldownAttachment(Slot slot) {
        return switch (slot) {
            case HEAD -> MJDataAttachments.HEAD_AUGMENTATION_COOLDOWN;
            case BODY -> MJDataAttachments.BODY_AUGMENTATION_COOLDOWN;
            case LEGS -> MJDataAttachments.LEGS_AUGMENTATION_COOLDOWN;
            case ARMS -> MJDataAttachments.ARMS_AUGMENTATION_COOLDOWN;
            case HEART -> MJDataAttachments.HEART_AUGMENTATION_COOLDOWN;
            case NONE -> null;
        };
    }

    private static Supplier<AttachmentType<Integer>> getAttachment(Slot slot) {
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
    }

    public static int getCooldown(Player player, Slot slot) {
        return player.getData(getCooldownAttachment(slot).get());
    }

    public static int getData(Player player, Slot slot) {
        return player.getData(getDataAttachment(slot).get());
    }

    public static void setId(Player player, Slot slot, int id) {
        player.setData(getAttachment(slot).get(), id);
    }

    public static void setCooldown(Player player, Slot slot, int cooldown) {
        player.setData(getCooldownAttachment(slot).get(), cooldown);
    }

    public static void setData(Player player, Slot slot, int data) {
        player.setData(getDataAttachment(slot).get(), data);
    }

    public static void setIdAndUpdate(Player player, Slot slot, int id) {
        if (player.level().isClientSide()) {
            PacketDistributor.sendToServer(new SetAugmentDataPayload(id, slot.slotId));
        } else {
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SetAugmentDataPayload(id, slot.slotId));
        }
        setId(player, slot, id);
    }

    public static void setCooldownAndUpdate(Player player, Slot slot, int cooldown) {
        ModJam.LOGGER.info("Setting Cooldown to {}", cooldown);
        if (player.level().isClientSide()) {
            PacketDistributor.sendToServer(new SetCooldownPayload(cooldown, slot.slotId));
        } else {
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SetCooldownPayload(cooldown, slot.slotId));
        }
        setCooldown(player, slot, cooldown);
    }

    public static void incId(Player player, Slot slot) {
        setIdAndUpdate(player, slot, AugmentHelper.getId(player, slot) + 1);
        player.sendSystemMessage(Component.literal("Incremented to Id " + getId(player, slot) + " for slot " + slot.name()));

    }

    public static void decId(Player player, Slot slot) {
        setIdAndUpdate(player, slot, AugmentHelper.getId(player, slot) - 1);
        player.sendSystemMessage(Component.literal("Decremented to Id " + getId(player, slot) + " for slot " + slot.name()));
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
    public static Integer[] getAugmentVals(Player player) {
        List<Integer> augments = new ArrayList<>();
        augments.add(getAugment(player, Slot.HEAD).getId());
        augments.add(getAugment(player, Slot.BODY).getId());
        augments.add(getAugment(player, Slot.ARMS).getId());
        augments.add(getAugment(player, Slot.LEGS).getId());
        augments.add(getAugment(player, Slot.HEART).getId());

        return augments.toArray(new Integer[10]);
    }
}
