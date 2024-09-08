package com.portingdeadmods.modjam.content.blocks.multiblock.part;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.DrainPartBlockEntity;
import com.portingdeadmods.modjam.content.items.AquarineWrenchItem;
import com.portingdeadmods.modjam.content.multiblocks.DrainMultiblock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.utils.BlockUtils;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class DrainPartBlock extends LaserBlock {
    public static final VoxelShape[] SHAPES;

    public static final BooleanProperty LASER_PORT = BooleanProperty.create("laser_port");
    public static final BooleanProperty TOP = BooleanProperty.create("top");

    public DrainPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(LASER_PORT, false)
                .setValue(TOP, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(DrainMultiblock.DRAIN_PART, Multiblock.FORMED, LASER_PORT, TOP));
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return p_49232_.getValue(TOP) ? RenderShape.INVISIBLE : RenderShape.MODEL;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(TOP) ? SHAPES[state.getValue(DrainMultiblock.DRAIN_PART)] : super.getShape(state, level, pos, context);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.DRAIN_PART.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DrainPartBlock::new);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof AquarineWrenchItem && !state.getValue(LASER_PORT) && state.getValue(DrainMultiblock.DRAIN_PART) % 2 != 0) {
            DrainPartBlock.setLaserPort(pos, level, hitResult.getDirection());
            return ItemInteractionResult.SUCCESS;
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
}
