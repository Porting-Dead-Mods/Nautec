package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

// TODO: implement this
public class WaterRegenAugment extends Augment {
    public WaterRegenAugment(AugmentSlot augmentSlot) {
        super(NTAugments.WATER_REGEN_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {
        if (player.isInWater() && player.tickCount % 20 == 0) {
            player.heal(1.0f);
        }
    }
}
