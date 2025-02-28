package com.portingdeadmods.nautec.api.blocks.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.blocks.DisplayBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class LaserBlock extends ContainerBlock implements DisplayBlock, SimpleWaterloggedBlock {
    public LaserBlock(Properties properties) {
        super(properties);
        if (waterloggable()) {
            registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
        }
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

    public abstract boolean waterloggable();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(waterloggable() ? builder.add(BlockStateProperties.WATERLOGGED) : builder);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (waterloggable() && state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = super.getStateForPlacement(context);
        return state != null && waterloggable()
                ? state.setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER)
                : state;
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState state) {
        return waterloggable() && state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        LaserBlockEntity laserBE = (LaserBlockEntity) level.getBlockEntity(blockPos);
        return List.of(
                Component.literal("Power: " + laserBE.getPower()).withStyle(ChatFormatting.WHITE),
                Component.literal("Purity: " + laserBE.getPurity()).withStyle(ChatFormatting.WHITE)
        );
    }

    // WATERLOGGED BLOCK METHODS


    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        if (waterloggable()) {
            return SimpleWaterloggedBlock.super.canPlaceLiquid(player, level, pos, state, fluid);
        }
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (waterloggable()) {
            return SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
        }
        return false;
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        if (waterloggable()) {
            return SimpleWaterloggedBlock.super.pickupBlock(player, level, pos, state);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return waterloggable() ? SimpleWaterloggedBlock.super.getPickupSound() : Optional.empty();
    }
}
