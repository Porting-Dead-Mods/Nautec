package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class StepUpAugment extends Augment {
    public StepUpAugment(AugmentSlot augmentSlot) {
        super(NTAugments.STEP_UP_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void onAdded(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.STEP_HEIGHT);
        attribute.setBaseValue(1.0f);
    }

    @Override
    public void onRemoved(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.STEP_HEIGHT);
        attribute.setBaseValue(0.6f);
    }
}
