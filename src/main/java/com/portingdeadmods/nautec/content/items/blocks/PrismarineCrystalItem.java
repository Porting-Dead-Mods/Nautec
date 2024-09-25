package com.portingdeadmods.nautec.content.items.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PrismarineCrystalItem extends BlockItem {
    public PrismarineCrystalItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        return super.place(BlockPlaceContext.at(context, context.getClickedPos().above(3), context.getNearestLookingDirection()));
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        BlockPos firstPos = context.getClickedPos().above(6);
        for (int i = 0; i < 6; i++) {
            BlockPos curPos = firstPos.below(i);
            if (!context.getLevel().getBlockState(curPos).canBeReplaced()) {
                return false;
            }
        }
        return super.canPlace(context, state);
    }
}
