package com.portingdeadmods.nautec.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CreativeEnergySourceBlock extends Block implements EntityBlock {
    public CreativeEnergySourceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        level.scheduleTick(pos, this, 1);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        level.scheduleTick(pos,this,1);
    }

    @Override
    protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity be = level.getBlockEntity(pos);
        return be != null && be.triggerEvent(id, param);
    }
}
