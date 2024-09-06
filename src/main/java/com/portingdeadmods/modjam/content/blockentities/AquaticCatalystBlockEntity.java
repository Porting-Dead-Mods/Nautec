package com.portingdeadmods.modjam.content.blockentities;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.api.utils.OptionalDirection;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.utils.MJBlockStateProperties;
import com.sun.source.tree.ModuleTree;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AquaticCatalystBlockEntity extends LaserBlockEntity {
    public AquaticCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.AQUATIC_CATALYST.get(), blockPos, blockState);
    }

    @Override
    public List<Direction> getLaserInputs() {
        return List.of();
    }

    @Override
    public List<Direction> getLaserOutputs() {
        OptionalDirection direction = getBlockState().getValue(MJBlockStateProperties.HOS_ACTIVE);
        if (direction != OptionalDirection.NONE) {
            return List.of(direction.getMcDirection().getOpposite());
        }
        return List.of();
    }

    @Override
    public int getLaserDistance(Direction direction) {
        return 5;
    }

    @Override
    public <T> ImmutableMap<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return ImmutableMap.of();
    }
}
