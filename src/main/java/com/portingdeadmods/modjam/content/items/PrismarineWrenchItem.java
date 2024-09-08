package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.modjam.utils.MultiblockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PrismarineWrenchItem extends Item {
    public PrismarineWrenchItem(Properties properties) {
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
                        return MultiblockHelper.form(multiblock, pos, level, useOnContext.getPlayer())
                                ? InteractionResult.SUCCESS
                                : InteractionResult.FAIL;
                    } catch (Exception e) {
                        ModJam.LOGGER.error("Encountered err forming multiblock", e);
                    }
                }
            }
        } else {
            // TODO: Multiblock unforming
            if (blockState.getBlock() instanceof DrainPartBlock) {
                level.setBlockAndUpdate(pos, blockState.setValue(DrainPartBlock.LASER_PORT, !blockState.getValue(DrainPartBlock.LASER_PORT)));
            }
        }
        return InteractionResult.FAIL;
    }
}
