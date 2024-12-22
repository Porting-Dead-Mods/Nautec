package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public class PreventFallDamageAugment extends Augment {
    public PreventFallDamageAugment(AugmentSlot augmentSlot) {
        super(NTAugments.PREVENT_FALL_DAMAGE_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void fall(LivingFallEvent event) {
        event.setDamageMultiplier(0.0f);
        event.setCanceled(true);
    }
}
