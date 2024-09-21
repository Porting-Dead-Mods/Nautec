package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.registries.MJAugments;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import org.jetbrains.annotations.Nullable;

public class PreventFallDamageAugment extends Augment {
    public PreventFallDamageAugment(AugmentSlot augmentSlot) {
        super(MJAugments.PREVENT_FALL_DAMAGE_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }

    @Override
    public void fall(LivingFallEvent event) {
        event.setDamageMultiplier(0.0f);
        event.setCanceled(true);
    }
}
