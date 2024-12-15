package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AnchorBlockEntity extends BlockEntity {
    public AnchorBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.ANCHOR.get(), pos, blockState);
    }
}
