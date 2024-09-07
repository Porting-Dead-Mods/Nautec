package com.portingdeadmods.modjam.api.blocks.blockentities;

import com.google.common.collect.ImmutableSet;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.registries.MJDataAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class LaserBlock extends ContainerBlock {
    public LaserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
//        ChunkAccess chunk = level.getChunk(pos);
//        Set<BlockPos> chunkLasers = chunk.getData(MJDataAttachments.CHUNK_LASERS);
//        ImmutableSet<BlockPos> newSet = ImmutableSet.<BlockPos>builder().addAll(chunkLasers).add(pos).build();
//        chunk.setData(MJDataAttachments.CHUNK_LASERS, newSet);
//        ModJam.LOGGER.debug("Chunk lasers: {}", chunkLasers);
    }

    @Override
    public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
    }
}
