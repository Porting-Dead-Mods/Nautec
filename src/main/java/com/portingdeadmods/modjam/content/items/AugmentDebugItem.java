package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import com.portingdeadmods.modjam.registries.MJDataAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class AugmentDebugItem extends Item {
    public AugmentDebugItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        player.sendSystemMessage(Component.literal("Head Id = " + AugmentHelper.getId(player, Slot.HEAD)));
        player.sendSystemMessage(Component.literal("Body Id = " + AugmentHelper.getId(player, Slot.BODY)));
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (level.getBlockState(context.getClickedPos()).is(Blocks.DIRT)){
            AugmentHelper.incId(player, Slot.HEAD);
        } else if (level.getBlockState(context.getClickedPos()).is(Blocks.STONE)){
            AugmentHelper.incId(player, Slot.BODY);
        }


        return super.useOn(context);
    }
}
