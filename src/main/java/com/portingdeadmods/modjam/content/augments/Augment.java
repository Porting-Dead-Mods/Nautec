package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@SuppressWarnings("unused")
public class Augment implements StaticAugment {

    @Override
    public void breakBlock(Slot slot, BlockEvent.BreakEvent event) {

    }

    @Override
    public void clientTick(Slot slot, PlayerTickEvent.Post event) {

    }

    @Override
    public void serverTick(Slot slot, PlayerTickEvent.Post event) {

    }

    @Override
    public void handleKeybindPress(Slot slot, Player player) {

    }

    @Override
    public int getId() {
        return -1;
    }
}
