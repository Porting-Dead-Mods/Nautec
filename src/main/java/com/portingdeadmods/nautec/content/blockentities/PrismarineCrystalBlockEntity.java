package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PrismarineCrystalBlockEntity extends LaserBlockEntity {
    public PrismarineCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.PRISMARINE_CRYSTAL.get(), blockPos, blockState);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of(
                Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
        );
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of(
                Direction.UP, Direction.DOWN
        );
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    protected void checkConnections() {
        for (Direction direction : getLaserOutputs()) {
            int maxLaserDistance = getMaxLaserDistance();
            for (int i = 1; i < maxLaserDistance; i++) {
                BlockPos pos = worldPosition.relative(direction, i);
                BlockState state = level.getBlockState(pos);

                if (level.getBlockEntity(pos) instanceof LaserBlockEntity laserBlockEntity) {
                    if (laserBlockEntity.getLaserInputs().contains(direction.getOpposite())) {
                        laserDistances.put(direction, i);
                        break;
                    }
                }

                if (!state.canBeReplaced() || i == maxLaserDistance -1) {
                    laserDistances.put(direction, i - (direction == Direction.DOWN ? 1 : 0));
                    break;
                }
            }
        }

    }

    @Override
    public void commonTick() {
        super.commonTick();

        setPurity(3f);
        transmitPower(this.getPower());
    }
}
