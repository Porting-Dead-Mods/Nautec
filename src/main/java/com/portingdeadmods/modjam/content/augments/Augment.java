package com.portingdeadmods.modjam.content.augments;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.level.BlockEvent;

@SuppressWarnings("unused")
public class Augment {
    public static void onBreak(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        event.setCanceled(true);
    }
}
