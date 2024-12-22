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
    }
}
