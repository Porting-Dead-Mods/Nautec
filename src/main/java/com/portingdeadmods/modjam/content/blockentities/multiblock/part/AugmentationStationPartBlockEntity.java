package com.portingdeadmods.modjam.content.blockentities.multiblock.part;

import com.portingdeadmods.modjam.api.blockentities.multiblock.MultiblockPartBlockEntity;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AugmentationStationPartBlockEntity extends MultiblockPartBlockEntity {
    public AugmentationStationPartBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MJBlockEntityTypes.AUGMENTATION_STATION_PART.get(), blockPos, blockState);
    }
}
