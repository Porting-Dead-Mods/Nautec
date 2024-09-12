package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class UnderwaterMovementSpeedAugment extends Augment{
    @Override
    public int getId() {
        return 6;
    }

    @Override
    public void serverTick(Slot slot, PlayerTickEvent.Post event) {
        if (event.getEntity().isUnderWater()){
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE,20,1));
        }
    }
}
