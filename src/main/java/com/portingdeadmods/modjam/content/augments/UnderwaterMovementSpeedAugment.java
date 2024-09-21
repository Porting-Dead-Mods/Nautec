package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.registries.MJAugments;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

public class UnderwaterMovementSpeedAugment extends Augment {
    public UnderwaterMovementSpeedAugment(AugmentSlot augmentSlot) {
        super(MJAugments.UNDERWATER_MOVEMENT_SPEED_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {
        if (event.getEntity().isUnderWater()){
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE,20,1));
        }
    }
}
