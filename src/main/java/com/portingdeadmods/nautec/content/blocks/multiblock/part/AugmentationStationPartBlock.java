package com.portingdeadmods.nautec.content.blocks.multiblock.part;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationPartBlockEntity;
import com.portingdeadmods.nautec.content.multiblocks.AugmentationStationMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
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
}
