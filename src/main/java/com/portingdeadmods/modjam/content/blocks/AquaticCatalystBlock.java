package com.portingdeadmods.modjam.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.DisplayBlock;
import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.api.utils.OptionalDirection;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.tags.MJTags;
import com.portingdeadmods.modjam.utils.ItemUtils;
import com.portingdeadmods.modjam.utils.MJBlockStateProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AquaticCatalystBlock extends LaserBlock implements DisplayBlock {
    public AquaticCatalystBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(MJBlockStateProperties.HOS_ACTIVE, OptionalDirection.NONE));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AquaticCatalystBlock::new);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.AQUATIC_CATALYST.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(MJBlockStateProperties.HOS_ACTIVE));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        OptionalDirection direction = OptionalDirection.fromMcDirection(hitResult.getDirection());
        if (state.getValue(MJBlockStateProperties.HOS_ACTIVE) == OptionalDirection.NONE) {
            // TODO: serialize stored item
            if (stack.is(MJTags.Items.AQUATIC_CATALYST)) {
                level.setBlockAndUpdate(pos, state.setValue(MJBlockStateProperties.HOS_ACTIVE, direction));
                level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 1);
                if (!player.hasInfiniteMaterials()) {
                    stack.shrink(1);
                }
                return ItemInteractionResult.SUCCESS;
            }
        } else if (stack.isEmpty() && direction == state.getValue(MJBlockStateProperties.HOS_ACTIVE)) {
            level.setBlockAndUpdate(pos, state.setValue(MJBlockStateProperties.HOS_ACTIVE, OptionalDirection.NONE));
            level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 1);
            if(!player.isCreative()){
                ItemUtils.giveItemToPlayerNoSound(player, Items.HEART_OF_THE_SEA.getDefaultInstance());
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        OptionalDirection direction = level.getBlockState(blockPos).getValue(MJBlockStateProperties.HOS_ACTIVE);
        return List.of(
                Component.literal("Active: "+ direction).withStyle(ChatFormatting.WHITE)
        );
    }
}
