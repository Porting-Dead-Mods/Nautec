package com.portingdeadmods.modjam.capabilities.augmentation;

import net.minecraft.nbt.CompoundTag;


public class PlayerAugmentationCapability implements IPlayerAugmentation {

    private int headId;
    private int bodyId;
    private int armsId;
    private int legsId;
    private int heartId;

    public int getAugment(AugmentationSlot slot){
        switch (slot){
            case LEGS -> {
                return legsId;
            }
            case ARMS -> {
                return armsId;
            }
            case BODY -> {
                return bodyId;
            }
            case HEAD -> {
                return headId;
            }
            case HEART -> {
                return heartId;
            }
        }
        return -2;
    }
    public void setAugment(AugmentationSlot slot, int value){
        switch (slot){
            case HEART -> heartId = value;
            case HEAD -> headId = value;
            case BODY -> bodyId = value;
            case ARMS -> armsId = value;
            case LEGS -> legsId = value;
        }
    }
    public void saveNBTData(CompoundTag tag){
        tag.putInt("heart_id", heartId);
        tag.putInt("head_id", headId);
        tag.putInt("body_id", bodyId);
        tag.putInt("arms_id", armsId);
        tag.putInt("legs_id", legsId);
    }
    public void loadNBTData(CompoundTag tag){
        heartId = tag.getInt("heart_id");
        headId = tag.getInt("head_id");
        bodyId = tag.getInt("body_id");
        armsId = tag.getInt("arms_id");
        legsId = tag.getInt("legs_id");
    }
}
