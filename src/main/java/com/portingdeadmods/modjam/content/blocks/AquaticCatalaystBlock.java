package com.portingdeadmods.modjam.content.blocks;

import com.portingdeadmods.modjam.api.utils.OptionalDirection;
import com.portingdeadmods.modjam.tags.MJTags;
import com.portingdeadmods.modjam.utils.ItemUtils;
import com.portingdeadmods.modjam.utils.MJBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class AquaticCatalaystBlock extends Block {
    public AquaticCatalaystBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(MJBlockStateProperties.HOS_ACTIVE, OptionalDirection.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(MJBlockStateProperties.HOS_ACTIVE));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        OptionalDirection direction = OptionalDirection.fromMcDirection(hitResult.getDirection());
        if (state.getValue(MJBlockStateProperties.HOS_ACTIVE) != direction) {
            // TODO: serialize stored item
            if (stack.is(MJTags.Items.AQUATIC_CATALYST)) {
                level.setBlockAndUpdate(pos, state.setValue(MJBlockStateProperties.HOS_ACTIVE, direction));
                level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 1);
                stack.shrink(1);
                return ItemInteractionResult.SUCCESS;
            }
        } else if (stack.isEmpty()) {
            level.setBlockAndUpdate(pos, state.setValue(MJBlockStateProperties.HOS_ACTIVE, OptionalDirection.NONE));
            level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.BLOCKS, 1, 1);
            ItemUtils.giveItemToPlayerNoSound(player, Items.HEART_OF_THE_SEA.getDefaultInstance());
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
