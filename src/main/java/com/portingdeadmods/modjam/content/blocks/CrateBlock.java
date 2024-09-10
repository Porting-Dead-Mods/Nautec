package com.portingdeadmods.modjam.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.content.blockentities.CrateBlockEntity;
import com.portingdeadmods.modjam.registries.MJBlocks;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.ShulkerBoxBlock.CONTENTS;

public class CrateBlock extends BaseEntityBlock {

    public static final BooleanProperty RUSTY = BooleanProperty.create("rusty");

    public CrateBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(RUSTY, false)
                .setValue(BlockStateProperties.OPEN, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(RUSTY, BlockStateProperties.OPEN));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CrateBlock::new);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) return ItemInteractionResult.sidedSuccess(true);

        if (!(level.getBlockEntity(pos) instanceof CrateBlockEntity be)
                || !stack.is(MJItems.CROWBAR)
                || state.getValue(BlockStateProperties.OPEN))
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (player.getCooldowns().isOnCooldown(stack.getItem())) return ItemInteractionResult.FAIL;

        RandomSource random = level.getRandom();

        if (random.nextInt(0, 7) == 0) {
            be.playSound(state, SoundEvents.ANVIL_HIT);
            player.getCooldowns().addCooldown(stack.getItem(), 10);
            return ItemInteractionResult.FAIL;
        }
        level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.OPEN, true));
        player.getCooldowns().addCooldown(stack.getItem(), 30);
        be.playSound(state, SoundEvents.ANVIL_USE);

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else if (level.getBlockEntity(pos) instanceof CrateBlockEntity be) {
            if (state.getValue(BlockStateProperties.OPEN)) {
                player.openMenu(be);
                player.awardStat(Stats.OPEN_BARREL);
                PiglinAi.angerNearbyPiglins(player, true);
            } else {
                be.playSound(state, SoundEvents.CHEST_LOCKED);
            }
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.1875, 0.8125, 0.75, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.125, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.1875, 0.1875, 0.125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.1875, 0.9375, 0.125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.8125, 0.9375, 0.125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.0625, 0.1875, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.0625, 0.9375, 0.75, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.125, 0.8125, 0.9375, 0.75, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.8125, 0.1875, 0.75, 0.9375), BooleanOp.OR);

        if (!state.getValue(BlockStateProperties.OPEN)) {
            shape = Shapes.join(shape, Shapes.box(0.1875, 0.75, 0.1875, 0.8125, 0.8125, 0.8125), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.8125, 0.9375, 0.875, 0.9375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.1875, 0.1875, 0.875, 0.8125), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.0625, 0.9375, 0.875, 0.1875), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.8125, 0.75, 0.1875, 0.9375, 0.875, 0.8125), BooleanOp.OR);
        }
        return shape;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrateBlockEntity(pos, state);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.isClientSide()) return super.playerWillDestroy(level, pos, state, player);
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof CrateBlockEntity be) {
            if (be.isEmpty() && !state.getValue(BlockStateProperties.OPEN)) {
                ItemStack itemstack = MJBlocks.CRATE.toStack();
                itemstack.applyComponents(blockentity.collectComponents());
                ItemEntity itementity = new ItemEntity(
                        level, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, itemstack
                );
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getValue(BlockStateProperties.OPEN)) {
            Containers.dropContentsOnDestroy(state, newState, level, pos);
            return;
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        BlockEntity blockentity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof CrateBlockEntity be) {
            params = params.withDynamicDrop(CONTENTS, p_56219_ -> {
                for (int i = 0; i < be.getContainerSize(); i++) {
                    p_56219_.accept(be.getItem(i));
                }
            });
        }

        return super.getDrops(state, params);
    }


    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof CrateBlockEntity) {
            ((CrateBlockEntity) blockentity).recheckOpen();
        }
    }
}
