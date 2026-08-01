package com.portingdeadmods.nautec.content.blockentities.multiblock.part;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.FakeBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.SavesControllerPosBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.DrainBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.BlockUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class DrainPartBlockEntity extends LaserBlockEntity implements FakeBlockEntity, SavesControllerPosBlockEntity {
    private BlockPos controllerPos;
    private Direction laserPort;

    public DrainPartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.DRAIN_PART.get(), blockPos, blockState);
    }

    public void setLaserPort(Direction laserPort) {
        this.laserPort = laserPort;
    }

    public boolean hasLaserPort() {
        return laserPort != null;
    }

    public void open() {
        BlockPos actualBlockEntityPos = getActualBlockEntityPos();
        Nautec.LOGGER.debug("Attempt to open");
        if (actualBlockEntityPos != null && level.getBlockEntity(actualBlockEntityPos) instanceof DrainBlockEntity drainBlockEntity) {
            drainBlockEntity.open();
        }
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of(laserPort);
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        if (laserPort != null) {
            return ObjectSet.of(laserPort.getOpposite());
        }
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
    protected void loadData(ValueInput in) {
        super.loadData(in);
        boolean hasControllerPos = in.getBooleanOr("hasControllerPos", false);
        boolean hasLaserPort = in.getBooleanOr("hasLaserPort", false);
        if (hasControllerPos) {
            this.controllerPos = BlockPos.of(in.getLongOr("controllerPos", 0));
        }

        if (hasLaserPort) {
            this.laserPort = Direction.values()[in.getIntOr("laserPort", 0)];
        }
    }

    @Override
    protected void saveData(ValueOutput out) {
        super.saveData(out);
        out.putBoolean("hasControllerPos", this.controllerPos != null);
        out.putBoolean("hasLaserPort", this.laserPort != null);

        if (this.controllerPos != null) {
            out.putLong("controllerPos", this.controllerPos.asLong());
        }

        if (this.laserPort != null) {
            out.putInt("laserPort", this.laserPort.ordinal());
        }
    }

    @Override
    public void commonTick() {
        super.commonTick();
        transmitPower(getPower());
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        BlockPos actualBlockEntityPos = getActualBlockEntityPos();
        if (actualBlockEntityPos != null && MultiblockEntity.UNFORMING.compareAndSet(false, true)) {
            try {
                MultiblockHelper.unform(NTMultiblocks.DRAIN.get(), actualBlockEntityPos, level, null);
            } finally {
                MultiblockEntity.UNFORMING.set(false);
            }
        }
        super.preRemoveSideEffects(pos, state);
    }
}
