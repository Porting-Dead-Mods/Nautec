package com.portingdeadmods.nautec.api.blockentities.multiblock;

import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import net.minecraft.nbt.CompoundTag;

import java.util.concurrent.atomic.AtomicBoolean;

public interface MultiblockEntity {
    AtomicBoolean UNFORMING = new AtomicBoolean();

    MultiblockData getMultiblockData();

    void setMultiblockData(MultiblockData data);

    default CompoundTag saveMBData() {
        return getMultiblockData().serializeNBT();
    }

    default MultiblockData loadMBData(CompoundTag tag) {
        return MultiblockData.deserializeNBT(tag);
    }
}