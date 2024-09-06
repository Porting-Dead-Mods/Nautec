package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.registries.MJDataAttachments;
import net.minecraft.world.entity.player.Player;

public class AugmentHelper {
    public static int getId(Player player, Slot slot){
        switch (slot) {
            case HEAD -> {
                return player.getData(MJDataAttachments.HEAD_AUGMENTATION);
            }
            case BODY -> {
                return player.getData(MJDataAttachments.BODY_AUGMENTATION);
            }
            case LEGS -> {
                return player.getData(MJDataAttachments.LEGS_AUGMENTATION);
            }
            case ARMS -> {
                return player.getData(MJDataAttachments.ARMS_AUGMENTATION);
            }
            case HEART -> {
                return player.getData(MJDataAttachments.HEART_AUGMENTATION);
            }
        }
        ModJam.LOGGER.warn("Error parsing Augment {}", slot.name());
        return -2;
    }

    public static void setId(Player player, Slot slot , int id){
        switch (slot){
            case HEAD -> {
                player.setData(MJDataAttachments.HEAD_AUGMENTATION, id);
            }
            case BODY -> {
                player.setData(MJDataAttachments.BODY_AUGMENTATION, id);
            }
            case LEGS -> {
                player.setData(MJDataAttachments.LEGS_AUGMENTATION, id);
            }
            case ARMS -> {
                player.setData(MJDataAttachments.ARMS_AUGMENTATION, id);
            }
            case HEART -> {
                player.setData(MJDataAttachments.HEART_AUGMENTATION, id);
            }
        }
    }
    public static void incId(Player player, Slot slot){
        setId(player, slot, AugmentHelper.getId(player, slot) + 1);
    }
}
