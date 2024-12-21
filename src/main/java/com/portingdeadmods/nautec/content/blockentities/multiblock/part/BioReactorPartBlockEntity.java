package com.portingdeadmods.nautec.content.blockentities.multiblock.part;

import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockPartEntity;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BioReactorPartBlockEntity extends BlockEntity implements MultiblockPartEntity {
    private BlockPos controllerPos;

    public BioReactorPartBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.BIO_REACTOR_PART.get(), pos, blockState);
    }

    @Override
    public BlockPos getControllerPos() {
        return this.controllerPos;
    }

    @Override
    public void setControllerPos(BlockPos blockPos) {
        this.controllerPos = blockPos;
    }
}
