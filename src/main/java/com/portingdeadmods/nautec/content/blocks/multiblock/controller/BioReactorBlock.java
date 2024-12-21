package com.portingdeadmods.nautec.content.blocks.multiblock.controller;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.multiblocks.BioReactorMultiblock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BioReactorBlock extends LaserBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BioReactorBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(Multiblock.FORMED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING, Multiblock.FORMED, BioReactorMultiblock.BIO_REACTOR_PART));
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.BIO_REACTOR.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BioReactorBlock::new);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state != null ? state.setValue(FACING, context.getHorizontalDirection()) : null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, BlockHitResult p_60508_) {
        return InteractionResult.FAIL;
    }
}
