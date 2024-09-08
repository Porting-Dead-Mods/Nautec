package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.utils.BlockUtils;
import com.portingdeadmods.modjam.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
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
        if (!useOnContext.getPlayer().isCrouching()) {
            for (Multiblock multiblock : MJRegistries.MULTIBLOCK) {
                if (controllerState.is(multiblock.getUnformedController())) {
                    try {
                        if (MultiblockHelper.form(multiblock, pos, level, useOnContext.getPlayer())) {
                            return InteractionResult.SUCCESS;
                        }
                        break;
                    } catch (Exception e) {
                        ModJam.LOGGER.error("Encountered err forming multiblock", e);
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
