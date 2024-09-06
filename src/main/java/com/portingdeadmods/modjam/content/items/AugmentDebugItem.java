package com.portingdeadmods.modjam.content.items;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import net.minecraft.client.Minecraft;
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
    public boolean onDroppedByPlayer(@NotNull ItemStack item, Player player) {
        player.sendSystemMessage(Component.literal("Head Id = " + AugmentHelper.getId(player, Slot.HEAD)));
        player.sendSystemMessage(Component.literal("Body Id = " + AugmentHelper.getId(player, Slot.BODY)));
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (clickedBlock == Blocks.DIRT){
            if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(),InputConstants.KEY_LSHIFT)){
                AugmentHelper.decId(player, Slot.HEAD);
            } else {
                AugmentHelper.incId(player, Slot.HEAD);
            }
        } else if (clickedBlock == Blocks.STONE){
            if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(),InputConstants.KEY_LSHIFT)){
                AugmentHelper.decId(player, Slot.BODY);
            } else {
                AugmentHelper.incId(player, Slot.BODY);
            }
        }


        return super.useOn(context);
    }
}
