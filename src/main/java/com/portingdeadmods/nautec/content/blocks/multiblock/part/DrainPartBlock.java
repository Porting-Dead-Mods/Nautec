package com.portingdeadmods.nautec.content.blocks.multiblock.part;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.DisplayBlock;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.nautec.content.items.tools.AquarineWrenchItem;
import com.portingdeadmods.nautec.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import com.portingdeadmods.nautec.utils.BlockUtils;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.enums.BubbleColumnDirection;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class DrainPartBlock extends LaserBlock implements DisplayBlock {
    public static final VoxelShape[] SHAPES;

    public static final BooleanProperty LASER_PORT = BooleanProperty.create("laser_port");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty HAS_POWER = BooleanProperty.create("enough_power");

    public DrainPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(LASER_PORT, false)
                .setValue(OPEN, false)
                .setValue(HAS_POWER, false)
        );
    }

    @Override
    public boolean waterloggable() {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(DrainMultiblock.DRAIN_PART, Multiblock.FORMED, LASER_PORT, OPEN, HAS_POWER));
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.UP && state.getValue(OPEN)) {
            level.scheduleTick(currentPos, this, 20);
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.DRAIN_PART.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DrainPartBlock::new);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult p_60508_) {
        if (state.getValue(Multiblock.FORMED) && level.getBlockEntity(pos) instanceof DrainPartBlockEntity drainPartBlockEntity) {
            BlockPos controllerPos = drainPartBlockEntity.getActualBlockEntityPos();
            if (controllerPos != null) {
                // Forward the interaction to the controller block
                BlockState controllerState = level.getBlockState(controllerPos);
                BlockHitResult newHitResult = new BlockHitResult(p_60508_.getLocation(), p_60508_.getDirection(), controllerPos, p_60508_.isInside());
                return controllerState.useWithoutItem(level, player, newHitResult);
            }
        }
        return super.useWithoutItem(state, level, pos, player, p_60508_);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof AquarineWrenchItem && !state.getValue(LASER_PORT) && state.getValue(DrainMultiblock.DRAIN_PART) % 2 != 0) {
            DrainPartBlock.setLaserPort(pos, level, hitResult.getDirection());
            return ItemInteractionResult.SUCCESS;
        }
        
        // Forward fluid container interactions to the controller when formed
        if (state.getValue(Multiblock.FORMED) && stack.getCapability(Capabilities.FluidHandler.ITEM) != null 
                && level.getBlockEntity(pos) instanceof DrainPartBlockEntity drainPartBlockEntity) {
            BlockPos controllerPos = drainPartBlockEntity.getActualBlockEntityPos();
            if (controllerPos != null) {
                // Forward the interaction to the controller block
                BlockState controllerState = level.getBlockState(controllerPos);
                BlockHitResult newHitResult = new BlockHitResult(hitResult.getLocation(), hitResult.getDirection(), controllerPos, hitResult.isInside());
                InteractionResult result = controllerState.useWithoutItem(level, player, newHitResult);
                return result == InteractionResult.SUCCESS ? ItemInteractionResult.SUCCESS : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        }
        
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    public static void setLaserPort(BlockPos partPos, Level level, Direction direction) {
        if (level.getBlockEntity(partPos) instanceof DrainPartBlockEntity drainPartBlockEntity) {
            BlockPos controllerPos = drainPartBlockEntity.getActualBlockEntityPos();
            BlockPos[] blocks = BlockUtils.getBlocksAroundSelfHorizontal(controllerPos);
            for (BlockPos pos : blocks) {
                if (level.getBlockEntity(pos) instanceof DrainPartBlockEntity drainPartBlockEntity1) {
                    drainPartBlockEntity1.setLaserPort(null);
                    level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(LASER_PORT, false));
                }
            }
            drainPartBlockEntity.setLaserPort(direction);
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(partPos, level.getBlockState(partPos).setValue(LASER_PORT, true));
            }
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (state.getValue(OPEN) && state.getValue(HAS_POWER)) {
            entity.hurt(level.damageSources().drown(), 4.0F);
        }
    }

    @Override
    public @NotNull BubbleColumnDirection getBubbleColumnDirection(BlockState state) {
        if (state.getValue(OPEN) && state.getValue(DrainPartBlock.HAS_POWER) && (state.getValue(DrainMultiblock.DRAIN_PART) == 4 || state.getValue(DrainMultiblock.DRAIN_PART) % 2 != 0)) {
            return BubbleColumnDirection.DOWNWARD;
        }
        return super.getBubbleColumnDirection(state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            DrainPartBlockEntity partBE = (DrainPartBlockEntity) level.getBlockEntity(pos);
            BlockPos actualBlockEntityPos = partBE.getActualBlockEntityPos();
            MultiblockHelper.unform(NTMultiblocks.DRAIN.get(), actualBlockEntityPos, level, null);
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    static {
        SHAPES = new VoxelShape[]{
                Shapes.or(Block.box(2, 0, 2, 16, 3, 16), Block.box(12, 3, 12, 16, 5, 16)),
                Shapes.or(Block.box(0, 0, 2, 16, 3, 16), Block.box(0, 3, 12, 16, 5, 16)),
                Shapes.or(Block.box(0, 0, 2, 14, 3, 16), Block.box(0, 3, 12, 4, 5, 16)),
                Shapes.or(Block.box(2, 0, 0, 16, 3, 16), Block.box(12, 3, 0, 16, 5, 16)),
                Stream.of(Block.box(0, 0, 0, 16, 3, 16), Block.box(0, 3, 0, 16, 5, 16), Block.box(6, 5, 6, 10, 9, 10), Block.box(1, 8.75, 1, 15, 9.25, 15)).reduce(Shapes::or).get(),
                Shapes.or(Block.box(0, 0, 0, 14, 3, 16), Block.box(0, 3, 0, 4, 5, 16)),
                Shapes.or(Block.box(2, 0, 0, 16, 3, 14), Block.box(12, 3, 0, 16, 5, 4)),
                Shapes.or(Block.box(0, 0, 0, 16, 3, 14), Block.box(0, 3, 0, 16, 5, 4)),
                Shapes.or(Block.box(0, 0, 0, 14, 3, 14), Block.box(0, 3, 0, 4, 5, 4))
        };
    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        if (level.getBlockEntity(blockPos) instanceof DrainPartBlockEntity drainPartBlockEntity) {
            BlockPos blockEntityPos = drainPartBlockEntity.getActualBlockEntityPos();
            IFluidHandler fluidHandler = level.getCapability(Capabilities.FluidHandler.BLOCK, blockEntityPos,
                    level.getBlockState(blockEntityPos),
                    level.getBlockEntity(blockEntityPos),
                    null
            );

            if (fluidHandler != null) {
                Component first = super.displayText(level, blockPos, player).getFirst();
                if (first != null) {
                    return List.of(
                            first,
                            Component.literal("Fluid Stored: " + fluidHandler.getFluidInTank(0).getAmount()).withStyle(ChatFormatting.WHITE)
                    );
                }
            }
        }
        return List.of();
    }
}
