package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class AugmentDebugItem extends Item {
    public AugmentDebugItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (level.isClientSide()) {
            if (clickedBlock == Blocks.DIRT) {
                AugmentHelper.incId(player, Slot.HEAD);
            } else if (clickedBlock == Blocks.GRASS_BLOCK) {
                AugmentHelper.decId(player, Slot.HEAD);
            } else if (clickedBlock == Blocks.STONE) {
                AugmentHelper.incId(player, Slot.BODY);
            } else if (clickedBlock == Blocks.SAND) {
                AugmentHelper.setCooldownAndUpdate(player, Slot.HEAD, 60);
            }
        }

        return super.useOn(context);
    }


}
