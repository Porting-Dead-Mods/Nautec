package com.portingdeadmods.modjam.content.augments;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@SuppressWarnings("unused")
public class Augment {
    public static void onBreak(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        event.setCanceled(true);
    }
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event){
        event.setNewSpeed(0.0f);
        event.setCanceled(true);
    }
    public static void onBlockLeftClick(PlayerInteractEvent.LeftClickBlock event){
        event.setCanceled(true);
    }
}
