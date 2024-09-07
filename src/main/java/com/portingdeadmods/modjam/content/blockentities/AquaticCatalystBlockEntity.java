package com.portingdeadmods.modjam.content.blockentities;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

public class AquaticCatalystBlockEntity extends LaserBlockEntity {
    public AquaticCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.AQUATIC_CATALYST.get(), blockPos, blockState);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        boolean coreActive = getBlockState().getValue(AquaticCatalystBlock.CORE_ACTIVE);
        if (coreActive) {
            return ObjectSet.of(direction.getOpposite());
        }
        return ObjectSet.of();
    }

    @Override
    public <T> ImmutableMap<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return ImmutableMap.of();
    }
}
