package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.DisplayBlock;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.content.blockentities.AquaticCatalystBlockEntity;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.utils.BlockUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AquaticCatalystBlock extends LaserBlock implements DisplayBlock {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 8);

    public AquaticCatalystBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.FACING, Direction.NORTH)
                .setValue(STAGE, 0)
        );
    }

    @Override
    public boolean waterloggable() {
        return false;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AquaticCatalystBlock::new);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.AQUATIC_CATALYST.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(BlockStateProperties.FACING, STAGE));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state != null ? state.setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite()) : null;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        AquaticCatalystBlockEntity be = (AquaticCatalystBlockEntity) level.getBlockEntity(pos);
        IItemHandler itemHandler = be.getItemHandler();
        if (!stack.isEmpty()) {
            boolean valid = itemHandler.isItemValid(0, stack);
            if (valid) {
                ItemStack remainder = itemHandler.insertItem(0, stack, false);
                player.setItemInHand(hand, remainder);

                return ItemInteractionResult.SUCCESS;
            }
        } else {
            ItemStack extracted = itemHandler.extractItem(0, itemHandler.getSlotLimit(0), false);
            ItemHandlerHelper.giveItemToPlayer(player, extracted, player.getInventory().selected);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    //    @Override
//    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
//        ItemStack itemStack = player.getMainHandItem();
//        if (itemStack.isEmpty()) {
//            itemStack = player.getOffhandItem();
//        }
//        Direction direction = hitResult.getDirection();
//        if (itemStack.isEmpty() && state.getValue(CORE_ACTIVE) && direction == state.getValue(BlockStateProperties.FACING)) {
//            level.setBlockAndUpdate(pos, state.setValue(CORE_ACTIVE, false));
//            level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 0.75f);
//            if (!player.isCreative()) {
//                ItemUtils.giveItemToPlayerNoSound(player, Items.HEART_OF_THE_SEA.getDefaultInstance());
//            }
//            return InteractionResult.SUCCESS;
//        }
//        return InteractionResult.FAIL;
//    }

//    @Override
//    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        Direction direction = hitResult.getDirection();
//        ItemStack itemStack = player.getItemInHand(hand);
//        if (!state.getValue(CORE_ACTIVE) && itemStack.is(NTTags.Items.AQUATIC_CATALYST)) {
//            // TODO: serialize stored item
//            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.FACING, direction).setValue(CORE_ACTIVE, true));
//            level.playLocalSound(player, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1, 1);
//            if (!player.hasInfiniteMaterials()) {
//                itemStack.shrink(1);
//            }
//            return ItemInteractionResult.SUCCESS;
//        }
//        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
//    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        BlockState blockState = level.getBlockState(blockPos);
        //Direction direction = blockState.getValue(BlockStateProperties.FACING);
        AquaticCatalystBlockEntity be = (AquaticCatalystBlockEntity) level.getBlockEntity(blockPos);
        return List.of(
                Component.literal("Duration: " + be.getDuration()).withStyle(ChatFormatting.WHITE)
        );
    }

}
