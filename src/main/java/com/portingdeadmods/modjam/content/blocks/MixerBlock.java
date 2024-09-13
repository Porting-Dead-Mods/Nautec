package com.portingdeadmods.modjam.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.api.utils.HorizontalDirection;
import com.portingdeadmods.modjam.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class MixerBlock extends LaserBlock {
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 2, 0, 2, 14, 16),
            Block.box(14, 2, 0, 16, 14, 16),
            Block.box(2, 2, 0, 14, 14, 2),
            Block.box(2, 2, 14, 14, 14, 16),
            Block.box(9, 14, 7, 16, 16, 9),
            Block.box(0, 14, 7, 7, 16, 9),
            Block.box(7, 14, 10, 9, 16, 16),
            Block.box(7, 14, 0, 9, 16, 6),
            Block.box(6, 14, 9, 10, 16, 10),
            Block.box(6, 14, 6, 10, 16, 7)
    ).reduce(Shapes::or).get();

    public MixerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.MIXER.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MixerBlock::new);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof MixerBlockEntity mixerBE) {
            IItemHandler itemHandler = mixerBE.getItemHandler();
            IFluidHandler fluidHandler = mixerBE.getFluidHandler();

            Direction clickedFace = player.getDirection();
            if (stack.isEmpty()) {
                return extractItemsSided(player, itemHandler, clickedFace);
            } else if (stack.getCapability(Capabilities.FluidHandler.ITEM) != null) {
                IFluidHandler itemFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM);
                if (mixerBE.getFluidHandler() instanceof FluidTank fluidTank) {
                    if (itemFluidHandler.getFluidInTank(0).isEmpty() && !fluidHandler.getFluidInTank(0).isEmpty()) {
                        extractFluid(player, level, hand, fluidTank, itemFluidHandler);
                    } else {
                        insertFluid(player, level, hand, fluidHandler, itemFluidHandler);
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            } else {
                return insertItemsSided(stack, player, hand, itemHandler, clickedFace);
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    private static void insertFluid(Player player, Level level, InteractionHand interactionHand, IFluidHandler fluidHandler, IFluidHandler fluidHandlerItem) {
        int filled = fluidHandler.fill(fluidHandlerItem.getFluidInTank(0).copy(), IFluidHandler.FluidAction.EXECUTE);
        FluidStack drained = fluidHandlerItem.drain(filled, IFluidHandler.FluidAction.EXECUTE);
        if (player.getItemInHand(interactionHand).getItem() instanceof BucketItem bucketItem && !drained.isEmpty()) {
            player.getItemInHand(interactionHand).shrink(1);
            ItemUtils.giveItemToPlayerNoSound(player, Items.BUCKET.getDefaultInstance());
            if (bucketItem.content.isSame(Fluids.WATER)) {
                level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.8F, 1.0F);
            } else if (bucketItem.content.isSame(Fluids.LAVA)) {
                level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.PLAYERS, 0.8F, 1.0F);
            }
        }
    }

    private static void extractFluid(Player player, Level level, InteractionHand interactionHand, FluidTank fluidHandler, IFluidHandler fluidHandlerItem) {
        FluidStack fluidInTank = fluidHandler.getFluidInTank(0);
        if (player.getItemInHand(interactionHand).is(Items.BUCKET)) {
            player.getItemInHand(interactionHand).shrink(1);
            ItemUtils.giveItemToPlayerNoSound(player, fluidInTank.getFluid().getBucket().getDefaultInstance());
            if (fluidInTank.is(Fluids.WATER)) {
                level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 0.8F, 1.0F);
            } else if (fluidInTank.is(Fluids.LAVA)) {
                level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.BUCKET_FILL_LAVA, SoundSource.PLAYERS, 0.8F, 1.0F);
            }
            fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE);
        } else {
            FluidStack fluidStack = fluidHandler.drain(fluidHandler.getFluidInTank(0).getAmount(), IFluidHandler.FluidAction.EXECUTE);
            int remainderAmount = fluidHandlerItem.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
            FluidStack newFluidStack = fluidStack.copy();
            newFluidStack.setAmount(remainderAmount);
            fluidHandler.setFluid(newFluidStack);
        }
    }

    private ItemInteractionResult insertItemsSided(ItemStack stack, Player player, InteractionHand hand, IItemHandler itemHandler, Direction clickedFace) {
        int slot = HorizontalDirection.fromRegularDirection(clickedFace).ordinal();
        ItemStack stackInSlot = itemHandler.getStackInSlot(slot);

        if (canInsert(stack, itemHandler, stackInSlot, slot)) {
            ItemStack itemStack = itemHandler.insertItem(slot, stack, false);
            player.setItemInHand(hand, itemStack);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private ItemInteractionResult extractItemsSided(Player player, IItemHandler itemHandler, Direction clickedFace) {
        int slot = HorizontalDirection.fromRegularDirection(clickedFace).ordinal();
        ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
        ItemStack itemStack = itemHandler.extractItem(slot, stackInSlot.getMaxStackSize(), false);
        if (!itemStack.isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, itemStack);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static boolean canInsert(ItemStack stack, IItemHandler itemHandler, ItemStack stackInSlot, int slot) {
        return stackInSlot.isEmpty()
                || (stackInSlot.is(stack.getItem())
                && stack.getCount() + stackInSlot.getCount() <= Math.min(itemHandler.getSlotLimit(slot), stack.getMaxStackSize()));
    }

    private static @NotNull ItemInteractionResult insertItems(ItemStack stack, Player player, InteractionHand hand, IItemHandler itemHandler) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stackInSlot = itemHandler.getStackInSlot(i);

            if (canInsert(stack, itemHandler, stackInSlot, i)) {
                ItemStack itemStack = itemHandler.insertItem(i, stack, false);
                player.setItemInHand(hand, itemStack);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static @NotNull ItemInteractionResult extractItems(Player player, IItemHandler itemHandler) {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stackInSlot = itemHandler.getStackInSlot(i);
            ItemStack itemStack = itemHandler.extractItem(i, stackInSlot.getMaxStackSize(), false);
            if (!itemStack.isEmpty()) {
                ModJam.LOGGER.debug("item: {}", itemStack);
                ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

}