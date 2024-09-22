package com.portingdeadmods.nautec.content.items.tools;

import com.portingdeadmods.nautec.MJRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.content.blocks.LaserJunctionBlock;
import com.portingdeadmods.nautec.utils.BlockUtils;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

public class AquarineWrenchItem extends Item {
    public AquarineWrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos pos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        BlockState controllerState = blockState;
        Player player = useOnContext.getPlayer();

        if (blockState.getBlock() instanceof LaserJunctionBlock && blockState.hasProperty(LaserJunctionBlock.CONNECTION[0])) {
            Direction direction = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE).getDirection();
            LaserJunctionBlock.ConnectionType newType = switch (blockState.getValue(LaserJunctionBlock.CONNECTION[direction.ordinal()])) {
                case INPUT ->
                        player.isShiftKeyDown() ? LaserJunctionBlock.ConnectionType.OUTPUT : LaserJunctionBlock.ConnectionType.NONE;
                case OUTPUT ->
                        player.isShiftKeyDown() ? LaserJunctionBlock.ConnectionType.NONE : LaserJunctionBlock.ConnectionType.INPUT;
                case NONE ->
                        player.isShiftKeyDown() ? LaserJunctionBlock.ConnectionType.OUTPUT : LaserJunctionBlock.ConnectionType.INPUT;
            };
            level.setBlockAndUpdate(pos, blockState.setValue(LaserJunctionBlock.CONNECTION[direction.ordinal()], newType));
            return InteractionResult.SUCCESS;
        }

        if (!useOnContext.getPlayer().isCrouching()) {
            for (Multiblock multiblock : MJRegistries.MULTIBLOCK) {
                if (controllerState.is(multiblock.getUnformedController())) {
                    try {
                        if (MultiblockHelper.form(multiblock, pos, level, useOnContext.getPlayer())) {
                            return InteractionResult.SUCCESS;
                        }
                        break;
                    } catch (Exception e) {
                        Nautec.LOGGER.error("Encountered err forming multiblock", e);
                    }
                }
            }

            for (Property<?> prop : blockState.getProperties()) {
                if (prop instanceof DirectionProperty directionProperty && prop.getName().equals("facing")) {
                    BlockState rotatedState = BlockUtils.rotateBlock(blockState, directionProperty, blockState.getValue(directionProperty));
                    level.setBlock(pos, rotatedState, 3);
                    level.playSound(null, pos, SoundEvents.ITEM_FRAME_ROTATE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
