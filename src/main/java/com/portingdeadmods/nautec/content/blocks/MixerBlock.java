package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.api.utils.HorizontalDirection;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

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
    public boolean waterloggable() {
        return true;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.MIXER.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MixerBlock::new);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof MixerBlockEntity mixerBE) {
            if (stack.getCapability(Capabilities.FluidHandler.ITEM) != null) {
                if (mixerBE.getFluidHandler() instanceof FluidTank fluidTank && mixerBE.getSecondaryFluidHandler() instanceof FluidTank secFluidTank) {
                    FluidTank targetTank = secFluidTank.getFluidInTank(0).isEmpty() ? fluidTank : secFluidTank;
                    
                    if (FluidUtil.interactWithFluidHandler(player, hand, targetTank)) {
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
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

}
