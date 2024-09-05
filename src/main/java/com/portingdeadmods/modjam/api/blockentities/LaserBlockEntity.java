package com.portingdeadmods.modjam.api.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class LaserBlockEntity extends ContainerBlockEntity {
    public LaserBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public abstract List<Direction> getLaserInputs();

    public abstract List<Direction> getLaserOutputs();

    public abstract int getLaserDistance();
}
