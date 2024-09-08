package com.portingdeadmods.modjam.content.augments;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface StaticAugment {
    void breakBlock(BlockEvent.BreakEvent event);
    void clientTick(PlayerTickEvent.Post event);
    void serverTick(PlayerTickEvent.Post event);
    void handleKeybindPress(Player player);
    int getId();
}
