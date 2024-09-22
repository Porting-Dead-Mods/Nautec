package com.portingdeadmods.nautec.content.blockentities.multiblock.controller;

import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AugmentationStationBlockEntity extends ContainerBlockEntity implements MultiblockEntity {
    private MultiblockData multiblockData;

    public AugmentationStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.AUGMENTATION_STATION.get(), blockPos, blockState);
        this.multiblockData = MultiblockData.EMPTY;
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public MultiblockData getMultiblockData() {
        return multiblockData;
    }

    @Override
    public void setMultiblockData(MultiblockData data) {
        this.multiblockData = data;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.multiblockData = loadMBData(tag.getCompound("multiblockData"));
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.put("multiblockData", saveMBData());
    }
}
