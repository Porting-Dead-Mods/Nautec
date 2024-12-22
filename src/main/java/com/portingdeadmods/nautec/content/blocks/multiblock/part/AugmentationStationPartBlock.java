package com.portingdeadmods.nautec.content.blocks.multiblock.part;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationPartBlockEntity;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.shapes.AugmentStationShapes;
import com.portingdeadmods.nautec.content.multiblocks.AugmentationStationMultiblock;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AugmentationStationPartBlock extends BaseEntityBlock {
    public AugmentationStationPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(Multiblock.FORMED, false)
                .setValue(AugmentationStationMultiblock.AS_PART, 0));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AugmentationStationPartBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AugmentationStationPartBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(Multiblock.FORMED, AugmentationStationMultiblock.AS_PART));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(AugmentationStationMultiblock.AS_PART).intValue() == 4) return super.getShape(state, level, pos, context);
        return AugmentStationShapes.getShape(state.getValue(AugmentationStationMultiblock.AS_PART).intValue()).getShapeModifier().apply(Shapes.empty());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && !newState.is(NTBlocks.POLISHED_PRISMARINE) && !newState.is(NTBlocks.AQUARINE_STEEL_BLOCK)) {
            Nautec.LOGGER.debug("unformingp");
            AugmentationStationPartBlockEntity be = (AugmentationStationPartBlockEntity) level.getBlockEntity(pos);
            MultiblockHelper.unform(NTMultiblocks.AUGMENTATION_STATION.get(), be.getControllerPos(), level);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
