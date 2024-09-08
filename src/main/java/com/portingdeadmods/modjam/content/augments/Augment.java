package com.portingdeadmods.modjam.content.augments;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@SuppressWarnings("unused")
public class Augment implements StaticAugment {

    @Override
    public void breakBlock(BlockEvent.BreakEvent event) {

    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {

    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {

    }

    @Override
    public void handleKeybindPress(Player player) {

    }

    @Override
    public int getId() {
        return -1;
    }
}
