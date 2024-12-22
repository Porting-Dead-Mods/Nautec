package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.entity.player.Player;

public class WalkingSpeedAugment extends Augment {
    public WalkingSpeedAugment(AugmentSlot augmentSlot) {
        super(NTAugments.WALKING_SPEED_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void onAdded(Player player) {
        player.getAbilities().setWalkingSpeed(0.25f);
    }

    @Override
    public void onRemoved(Player player) {
        player.getAbilities().setWalkingSpeed(0.1f);
    }
}
