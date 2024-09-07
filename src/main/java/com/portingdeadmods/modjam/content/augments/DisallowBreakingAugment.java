package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class DisallowBreakingAugment implements StaticAugment{
    @Override
    public void breakBlock(BlockEvent.BreakEvent event) {
        // Player player = event.getPlayer();
        event.setCanceled(true);
    }

    @Override
    public void tick(PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_Y)){
            event.getEntity().addItem(Items.EMERALD.getDefaultInstance());
        }
    }
}
