package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class WalkingSpeedAugment extends Augment {
    public WalkingSpeedAugment(AugmentSlot augmentSlot) {
        super(NTAugments.WALKING_SPEED_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
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
