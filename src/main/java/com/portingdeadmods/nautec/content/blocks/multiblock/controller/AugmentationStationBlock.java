package com.portingdeadmods.nautec.content.blocks.multiblock.controller;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.ContainerBlock;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.multiblocks.AugmentationStationMultiblock;
import com.portingdeadmods.nautec.registries.MJBlockEntityTypes;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class AugmentationStationBlock extends ContainerBlock {
    public AugmentationStationBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(Multiblock.FORMED, false)
                .setValue(AugmentationStationMultiblock.AS_PART, 0));
    }

    @Override
    public boolean tickingEnabled() {
        return false;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.AUGMENTATION_STATION.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AugmentationStationBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(Multiblock.FORMED, AugmentationStationMultiblock.AS_PART));
    }
}
