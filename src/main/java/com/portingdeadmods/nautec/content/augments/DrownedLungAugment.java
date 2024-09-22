package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.MJAugmentSlots;
import com.portingdeadmods.nautec.registries.MJAugments;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

public class DrownedLungAugment extends Augment {
    public DrownedLungAugment(AugmentSlot augmentSlot) {
        super(MJAugments.PREVENT_PLAYER_LOSE_AIR_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[] {
                MJAugmentSlots.LUNG.get()
        };
    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if(player.isUnderWater()){
            player.setAirSupply(player.getMaxAirSupply());
        }
    }
}
