package com.portingdeadmods.modjam.content.blocks;

import com.portingdeadmods.modjam.utils.MJBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class AquaticCatalaystBlock extends Block {
    public AquaticCatalaystBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(MJBlockStateProperties.HOS_ACTIVE));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        // TODO: Use a tag for this
        if (stack.is(Items.HEART_OF_THE_SEA)) {
            level.setBlockAndUpdate(pos, state.setValue(MJBlockStateProperties.HOS_ACTIVE, player.getDirection().getOpposite()));
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
