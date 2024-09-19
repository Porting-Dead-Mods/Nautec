package com.portingdeadmods.modjam.content.blockentities.multiblock.part;

import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.api.blockentities.multiblock.MultiblockPartEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AugmentationStationExtensionBlockEntity extends LaserBlockEntity implements MultiblockPartEntity {
    private boolean hasRobotArm;
    private BlockPos controllerPos;

    public AugmentationStationExtensionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.AUGMENTATION_STATION_EXTENSION.get(), blockPos, blockState);
        // Robot arm
        addItemHandler(1);
    }

    public boolean hasRobotArm() {
        return hasRobotArm;
    }

    public void setHasRobotArm(boolean hasRobotArm) {
        this.hasRobotArm = hasRobotArm;
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public BlockPos getControllerPos() {
        return controllerPos;
    }

    @Override
    public void setControllerPos(BlockPos blockPos) {
        this.controllerPos = blockPos;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.controllerPos = BlockPos.of(tag.getLong("controllerPos"));
        this.hasRobotArm = tag.getBoolean("hasRobotArm");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putLong("controllerPos", controllerPos.asLong());
        tag.putBoolean("hasRobotArm", hasRobotArm);
    }
}
