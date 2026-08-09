package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class AbyssalEyesAugment extends Augment {
    private static final int DURATION = 220;

    public AbyssalEyesAugment(AugmentSlot augmentSlot) {
        super(NTAugments.ABYSSAL_EYES.get(), augmentSlot);
    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.tickCount % 40 != 0 || player.getY() > NTConfig.abyssalEyesDepth) {
            return;
        }
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, DURATION, 0, true, false, false));
    }

    @Override
    public void onRemoved(Player player) {
        player.removeEffect(MobEffects.NIGHT_VISION);
    }
}
