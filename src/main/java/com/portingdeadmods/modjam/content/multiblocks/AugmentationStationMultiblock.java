package com.portingdeadmods.modjam.content.multiblocks;

import com.portingdeadmods.modjam.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.api.multiblocks.MultiblockData;
import com.portingdeadmods.modjam.api.multiblocks.MultiblockLayer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AugmentationStationMultiblock implements Multiblock {
    @Override
    public Block getUnformedController() {
        return null;
    }

    @Override
    public Block getFormedController() {
        return null;
    }

    @Override
    public MultiblockLayer[] getLayout() {
        return new MultiblockLayer[0];
    }

    @Override
    public Int2ObjectMap<Block> getDefinition() {
        return null;
    }

    @Override
    public BlockEntityType<? extends MultiblockEntity> getMultiBlockEntityType() {
        return null;
    }

    @Override
    public @Nullable BlockState formBlock(Level level, BlockPos blockPos, BlockPos controllerPos, int layerIndex, int layoutIndex, MultiblockData multiblockData, @Nullable Player player) {
        return null;
    }

    @Override
    public boolean isFormed(Level level, BlockPos blockPos) {
        return false;
    }
}
