package com.portingdeadmods.nautec.content.multiblocks;

import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.SavesControllerPosBlockEntity;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockData;
import com.portingdeadmods.nautec.api.multiblocks.MultiblockLayer;
import com.portingdeadmods.nautec.api.utils.HorizontalDirection;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class DrainMultiblock implements Multiblock {
    public static final IntegerProperty DRAIN_PART = IntegerProperty.create("drain_part", 0, 8);

    @Override
    public Block getUnformedController() {
        return NTBlocks.DRAIN.get();
    }

    @Override
    public Block getFormedController() {
        return NTBlocks.DRAIN.get();
    }

    @Override
    public MultiblockLayer[] getLayout() {
        return new MultiblockLayer[]{
                layer(
                        0, 0, 0,
                        0, 1, 0,
                        0, 0, 0
                )
        };
    }

    @Override
    public Int2ObjectMap<Block> getDefinition() {
        Int2ObjectMap<Block> def = new Int2ObjectOpenHashMap<>();
        def.put(0, NTBlocks.DRAIN_WALL.get());
        def.put(1, getUnformedController());
        return def;
    }

    @Override
    public BlockEntityType<? extends MultiblockEntity> getMultiBlockEntityType() {
        return NTBlockEntityTypes.DRAIN.get();
    }

    @Override
    public @Nullable BlockState formBlock(Level level, BlockPos blockPos, BlockPos controllerPos, int layerIndex, int layoutIndex, MultiblockData multiblockData, @Nullable Player player) {
        if (layerIndex == 4) {
            return getFormedController().defaultBlockState().setValue(FORMED, true);
        } else {
            return NTBlocks.DRAIN_PART.get().defaultBlockState()
                    .setValue(FORMED, true)
                    .setValue(DRAIN_PART, layerIndex);
        }
    }

    @Override
    public void afterFormBlock(Level level, BlockPos blockPos, BlockPos controllerPos, int layerIndex, int layoutIndex, MultiblockData multiblockData, @Nullable Player player) {
        level.setBlockAndUpdate(blockPos.above(), NTBlocks.DRAIN_PART.get().defaultBlockState()
                .setValue(DrainPartBlock.TOP, true)
                .setValue(FORMED, true)
                .setValue(DRAIN_PART, layerIndex)
                .setValue(DrainPartBlock.WATERLOGGED, level.getBlockState(blockPos).getFluidState().is(FluidTags.WATER))
                .setValue(DrainPartBlock.OPEN, false)
        );
        BlockEntity be = level.getBlockEntity(blockPos.above());
        if (be instanceof SavesControllerPosBlockEntity savesControllerPosBE) {
            savesControllerPosBE.setControllerPos(controllerPos);
        }
    }

    @Override
    public void afterUnformBlock(Level level, BlockPos blockPos, BlockPos controllerPos, int layerIndex, int layoutIndex, HorizontalDirection direction, @Nullable Player player) {
        Multiblock.super.afterUnformBlock(level, blockPos, controllerPos, layerIndex, layoutIndex, direction, player);
        level.removeBlock(blockPos.above(), false);
    }

    @Override
    public boolean isFormed(Level level, BlockPos blockPos) {
        BlockState block = level.getBlockState(blockPos);
        return block.hasProperty(FORMED) && block.getValue(FORMED);
    }

    @Override
    public @Nullable HorizontalDirection getFixedDirection() {
        return HorizontalDirection.NORTH;
    }
}