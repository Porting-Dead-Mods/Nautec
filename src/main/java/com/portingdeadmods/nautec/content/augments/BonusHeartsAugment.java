package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class BonusHeartsAugment extends Augment {
    public BonusHeartsAugment(AugmentSlot augmentSlot) {
        super(NTAugments.BONUS_HEART_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void onAdded(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        attribute.setBaseValue(40);
    }

    @Override
    public void onRemoved(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        attribute.setBaseValue(20);
    }
}
