package com.portingdeadmods.modjam.content.blockentities;

import com.google.common.collect.ImmutableMap;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.capabilities.IOActions;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

public class ExampleBE extends ContainerBlockEntity {
    public ExampleBE(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.EXAMPLE_BE.get(), blockPos, blockState);
        addFluidTank(2000);
    }

    @Override
    public <T> ImmutableMap<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return ImmutableMap.of(
                Direction.UP, Pair.of(IOActions.INSERT, new int[]{0})
        );
    }
}
