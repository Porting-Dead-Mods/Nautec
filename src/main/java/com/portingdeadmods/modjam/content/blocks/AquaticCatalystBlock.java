package com.portingdeadmods.modjam.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.DisplayBlock;
import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.tags.MJTags;
import com.portingdeadmods.modjam.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AquaticCatalystBlock extends LaserBlock implements DisplayBlock {
    public static final BooleanProperty CORE_ACTIVE = BooleanProperty.create("core_active");

    public AquaticCatalystBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.FACING, Direction.NORTH)
                .setValue(CORE_ACTIVE, false)
        );
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
        super.createBlockStateDefinition(builder.add(BlockStateProperties.FACING, CORE_ACTIVE));
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.isEmpty()) {
            itemStack = player.getOffhandItem();
        }
        Direction direction = hitResult.getDirection();
        if (itemStack.isEmpty() && state.getValue(CORE_ACTIVE) && direction == state.getValue(BlockStateProperties.FACING)) {
            level.setBlockAndUpdate(pos, state.setValue(CORE_ACTIVE, false));
            level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 0.75f);
            if (!player.isCreative()) {
                ItemUtils.giveItemToPlayerNoSound(player, Items.HEART_OF_THE_SEA.getDefaultInstance());
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Direction direction = hitResult.getDirection();
        ItemStack itemStack = player.getItemInHand(hand);
        if (!state.getValue(CORE_ACTIVE) && itemStack.is(MJTags.Items.AQUATIC_CATALYST)) {
            // TODO: serialize stored item
            ModJam.LOGGER.debug("If");
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.FACING, direction).setValue(CORE_ACTIVE, true));
            level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 1);
            if (!player.hasInfiniteMaterials()) {
                itemStack.shrink(1);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        BlockState blockState = level.getBlockState(blockPos);
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        boolean coreActive = blockState.getValue(CORE_ACTIVE);
        return List.of(
                Component.literal("Active: " + (coreActive ? direction : "None")).withStyle(ChatFormatting.WHITE)
        );
    }

}
