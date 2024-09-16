package com.portingdeadmods.modjam.content.blockentities;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.content.blocks.LaserJunctionBlock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class LaserJunctionBlockEntity extends LaserBlockEntity {
    public LaserJunctionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.LASER_JUNCTION.get(), blockPos, blockState);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return getConnections(LaserJunctionBlock.ConnectionType.INPUT);
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return getConnections(LaserJunctionBlock.ConnectionType.OUTPUT);
    }

    // TODO: Cache this
    private ObjectSet<Direction> getConnections(LaserJunctionBlock.ConnectionType type) {
        ObjectSet<Direction> connections = new ObjectArraySet<>();
        for (Direction direction : Direction.values()) {
            if (getBlockState().getValue(LaserJunctionBlock.CONNECTION[direction.get3DDataValue()]) == type) {
                connections.add(direction);
            }
        }
        return connections;
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        transmitPower(this.power);
    }
}
