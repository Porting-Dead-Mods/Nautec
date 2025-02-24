package com.portingdeadmods.nautec.content.blockentities.multiblock.semi;

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
import java.util.Set;

public class PrismarineCrystalBlockEntity extends LaserBlockEntity {
    private boolean breaking;
    private long startTick;
    private int duration;

    public PrismarineCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.PRISMARINE_CRYSTAL.get(), blockPos, blockState);
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of(
                Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
        );
    }

    @Override
    public Set<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();

        setPurity(3f);

        if (duration > 0 && isBreaking()) {
            duration--;
            if (duration == 0) {
                this.breaking = false;
            }
        }
    }

    public boolean isBreaking() {
        return breaking;
    }

    public long getStartTick() {
        return startTick;
    }

    public int getDuration() {
        return duration;
    }

    public void playBreakAnimation() {
        this.breaking = true;
        this.startTick = level.getGameTime();
        this.duration = 10;
    }
}
