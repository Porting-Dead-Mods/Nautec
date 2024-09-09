package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface StaticAugment {
    void breakBlock(Slot slot, BlockEvent.BreakEvent event);
    void clientTick(Slot slot, PlayerTickEvent.Post event);
    void serverTick(Slot slot, PlayerTickEvent.Post event);
    void handleKeybindPress(Slot slot, Player player);
    int getId();
}
