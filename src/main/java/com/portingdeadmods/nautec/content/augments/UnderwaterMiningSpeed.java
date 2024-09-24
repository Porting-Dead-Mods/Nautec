package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class UnderwaterMiningSpeed extends Augment {
    public UnderwaterMiningSpeed(AugmentSlot augmentSlot) {
        super(NTAugments.UNDERWATER_MINING_SPEED_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }

    @Override
    public void onAdded(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.SUBMERGED_MINING_SPEED);
        attribute.setBaseValue(1.0f);
    }

    @Override
    public void onRemoved(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.SUBMERGED_MINING_SPEED);
        attribute.setBaseValue(0.1f);
    }
}
