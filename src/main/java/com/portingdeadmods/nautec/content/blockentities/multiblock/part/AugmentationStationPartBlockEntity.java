package com.portingdeadmods.nautec.content.blockentities.multiblock.part;

import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockPartEntity;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AugmentationStationPartBlockEntity extends BlockEntity implements MultiblockPartEntity {
    private BlockPos controllerPos;

    public AugmentationStationPartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.AUGMENTATION_STATION_PART.get(), blockPos, blockState);
    }

    @Override
    public BlockPos getControllerPos() {
        return controllerPos;
    }

    @Override
    public void setControllerPos(BlockPos blockPos) {
        this.controllerPos = blockPos;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.controllerPos = BlockPos.of(tag.getLong("controllerPos"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putLong("controllerPos", controllerPos.asLong());
    }
}
