package com.portingdeadmods.nautec.content.blocks.multiblock.part;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.BioReactorPartBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.controller.BioReactorBlock;
import com.portingdeadmods.nautec.content.multiblocks.BioReactorMultiblock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BioReactorPartBlock extends LaserBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BioReactorPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(Multiblock.FORMED, false)
                .setValue(BioReactorMultiblock.TOP, false)
                .setValue(BioReactorMultiblock.HATCH, false)
        );
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.BIO_REACTOR_PART.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(
                FACING,
                Multiblock.FORMED,
                BioReactorMultiblock.BIO_REACTOR_PART,
                BioReactorMultiblock.TOP,
                BioReactorMultiblock.HATCH
        ));
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
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BioReactorPartBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof BioReactorPartBlockEntity be) {
            BlockPos controllerPos = be.getControllerPos();
            if (controllerPos != null) {
                MultiblockHelper.unform(NTMultiblocks.BIO_REACTOR.get(), controllerPos, level);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}