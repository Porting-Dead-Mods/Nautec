package com.portingdeadmods.modjam.content.blocks.multiblock.part;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class DrainPartBlock extends LaserBlock {
    public static final BooleanProperty LASER_PORT = BooleanProperty.create("laser_port");

    public DrainPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(DrainPartBlock.LASER_PORT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(DrainMultiblock.DRAIN_PART, Multiblock.FORMED, LASER_PORT));
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.DRAIN_PART.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DrainPartBlock::new);
    }
}
