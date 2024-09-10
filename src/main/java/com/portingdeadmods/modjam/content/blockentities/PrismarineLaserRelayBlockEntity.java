package com.portingdeadmods.modjam.content.blockentities;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.modjam.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.content.blocks.PrismarineLaserRelayBlock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

public class PrismarineLaserRelayBlockEntity extends LaserBlockEntity {
    public PrismarineLaserRelayBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.PRISMARINE_LASER_RELAY.get(), blockPos, blockState);
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of(
                getBlockState().getValue(PrismarineLaserRelayBlock.FACING).getOpposite()
        );
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of(
                getBlockState().getValue(PrismarineLaserRelayBlock.FACING)
        );
    }

    @Override
    public <T> ImmutableMap<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return ImmutableMap.of();
    }
}