package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class PreventPlayerLoseAirAugment extends Augment{
    @Override
    public int getId() {
        return 5;
    }

    @Override
    public void serverTick(Slot slot, PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if(player.isUnderWater()){
            player.setAirSupply(player.getMaxAirSupply());
        }
    }
}
