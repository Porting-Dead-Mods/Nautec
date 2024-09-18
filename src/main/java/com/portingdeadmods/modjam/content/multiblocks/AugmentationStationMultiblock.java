package com.portingdeadmods.modjam.content.multiblocks;

import com.portingdeadmods.modjam.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.api.multiblocks.MultiblockData;
import com.portingdeadmods.modjam.api.multiblocks.MultiblockLayer;
import com.portingdeadmods.modjam.api.utils.HorizontalDirection;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.registries.MJBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class AugmentationStationMultiblock implements Multiblock {
    public static final IntegerProperty AS_PART = IntegerProperty.create("as_part", 0, 8);

    @Override
    public Block getUnformedController() {
        return MJBlocks.AUGMENTATION_STATION.get();
    }

    @Override
    public Block getFormedController() {
        return MJBlocks.AUGMENTATION_STATION.get();
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
        def.put(0, Blocks.DARK_PRISMARINE);
        def.put(1, getUnformedController());
        return def;
    }

    @Override
    public BlockEntityType<? extends MultiblockEntity> getMultiBlockEntityType() {
        return MJBlockEntityTypes.AUGMENTATION_STATION.get();
    }

    @Override
    public @Nullable BlockState formBlock(Level level, BlockPos blockPos, BlockPos controllerPos, int layerIndex, int layoutIndex, MultiblockData multiblockData, @Nullable Player player) {
        if (layerIndex == 4) {
            return getFormedController().defaultBlockState().setValue(FORMED, true).setValue(AS_PART, layerIndex);
        }
        return MJBlocks.AUGMENTATION_STATION_PART.get().defaultBlockState().setValue(FORMED, true).setValue(AS_PART, layerIndex);
    }

    @Override
    public boolean isFormed(Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        return blockState.hasProperty(FORMED) && blockState.getValue(FORMED);
    }

    @Override
    public @Nullable HorizontalDirection getFixedDirection() {
        return HorizontalDirection.EAST;
    }
}
