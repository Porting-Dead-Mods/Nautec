package com.portingdeadmods.modjam.api.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public abstract class LaserBlockEntity extends ContainerBlockEntity {
    private int laserAnimTime;

    public LaserBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public abstract List<Direction> getLaserInputs();

    public abstract List<Direction> getLaserOutputs();

    public abstract int getLaserDistance(Direction direction);

    public int getLaserAnimTimeDuration() {
        return 80;
    }

    public int getLaserAnimTime() {
        return laserAnimTime;
    }

    public float getLaserScale(float partialTick) {
        return ((float) this.laserAnimTime + partialTick) / (float) this.getLaserAnimTimeDuration();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        if (laserAnimTime < getLaserAnimTimeDuration()) {
            this.laserAnimTime++;
        } else {
            this.laserAnimTime = 0;
        }
    }
}
