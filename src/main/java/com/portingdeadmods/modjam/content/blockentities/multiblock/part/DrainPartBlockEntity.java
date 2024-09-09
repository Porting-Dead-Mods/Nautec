package com.portingdeadmods.modjam.content.blockentities.multiblock.part;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.api.blockentities.multiblock.FakeBlockEntity;
import com.portingdeadmods.modjam.api.blockentities.multiblock.SavesControllerPosBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.modjam.content.blocks.multiblock.controller.DrainBlock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

public class DrainPartBlockEntity extends LaserBlockEntity implements FakeBlockEntity, SavesControllerPosBlockEntity {
    private BlockPos controllerPos;
    private Direction laserPort;

    public DrainPartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.DRAIN_PART.get(), blockPos, blockState);
    }

    public void setLaserPort(Direction laserPort) {
        this.laserPort = laserPort;
    }

    public boolean hasLaserPort() {
        return laserPort != null;
    }

    public void open() {
        BlockPos actualBlockEntityPos = getActualBlockEntityPos();
        ModJam.LOGGER.debug("Attempt to open");
        if (actualBlockEntityPos != null && level.getBlockEntity(actualBlockEntityPos) instanceof DrainBlockEntity drainBlockEntity) {
            drainBlockEntity.open();
        }
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of(laserPort);
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public <T> ImmutableMap<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return ImmutableMap.of();
    }

    @Override
    public boolean actualBlockEntity() {
        return false;
    }

    @Override
    public @Nullable BlockPos getActualBlockEntityPos() {
        return this.controllerPos;
    }

    @Override
    public void setControllerPos(BlockPos blockPos) {
        this.controllerPos = blockPos;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        boolean hasControllerPos = tag.getBoolean("hasControllerPos");
        boolean hasLaserPort = tag.getBoolean("hasLaserPort");
        if (hasControllerPos) {
            this.controllerPos = BlockPos.of(tag.getLong("controllerPos"));
        }

        if (hasLaserPort) {
            this.laserPort = Direction.values()[tag.getInt("laserPort")];
        }
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putBoolean("hasControllerPos", this.controllerPos != null);
        tag.putBoolean("hasLaserPort", this.laserPort != null);

        if (this.controllerPos != null) {
            tag.putLong("controllerPos", this.controllerPos.asLong());
        }

        if (this.laserPort != null) {
            tag.putInt("laserPort", this.laserPort.ordinal());
        }
    }
}
