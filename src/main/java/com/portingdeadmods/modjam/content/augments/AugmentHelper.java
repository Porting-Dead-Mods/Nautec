package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.registries.MJDataAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class AugmentHelper {
    private static final HashMap<Integer, StaticAugment> augmentHashMap = new HashMap<>();

    public static void AddAugment(StaticAugment augment, int id){
        augmentHashMap.put(id, augment);
    }
    public static StaticAugment getAugment(Player player, Slot slot){
        int id = getId(player, slot);
        return augmentHashMap.get(id);
    }
    private static Supplier<AttachmentType<Integer>> getAttachment(Slot slot){
        switch (slot){
            case HEAD -> {
                return MJDataAttachments.HEAD_AUGMENTATION;
            }
            case BODY -> {
                return MJDataAttachments.BODY_AUGMENTATION;
            }
            case LEGS -> {
                return MJDataAttachments.LEGS_AUGMENTATION;
            }
            case ARMS -> {
                return MJDataAttachments.ARMS_AUGMENTATION;
            }
            case HEART -> {
                return MJDataAttachments.HEART_AUGMENTATION;
            }
        }
        ModJam.LOGGER.warn("Error parsing slot {} (I thought this was unreachable)", slot.name());
        return MJDataAttachments.HEAD_AUGMENTATION;
    }
    public static int getId(Player player, Slot slot){
        return player.getData(getAttachment(slot));
        // return -2;
    }
    public static void setId(Player player, Slot slot , int id){
        player.setData(getAttachment(slot), id);
    }
    public static void incId(Player player, Slot slot){
        setId(player, slot, AugmentHelper.getId(player, slot) + 1);
        player.sendSystemMessage(Component.literal("Incremented to Id "+getId(player, slot)+" for slot "+slot.name()));

    }
    public static void decId(Player player, Slot slot){
        setId(player, slot, AugmentHelper.getId(player, slot) - 1);
        player.sendSystemMessage(Component.literal("Decremented to Id "+getId(player, slot)+" for slot "+slot.name()));
    }
    public static StaticAugment getAugment(int id){
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
