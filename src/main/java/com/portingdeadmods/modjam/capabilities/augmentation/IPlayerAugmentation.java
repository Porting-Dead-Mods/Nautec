package com.portingdeadmods.modjam.capabilities.augmentation;

import net.minecraft.nbt.CompoundTag;

public interface IPlayerAugmentation {
    int getAugment(AugmentationSlot slot);
    void setAugment(AugmentationSlot slot, int value);
    void saveNBTData(CompoundTag tag);
    void loadNBTData(CompoundTag tag);
}
