package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class PhotophoreSkinAugment extends Augment {
    private static final int INTERVAL = 20;
    private static final int DURATION = 60;

    public PhotophoreSkinAugment(AugmentSlot augmentSlot) {
        super(NTAugments.PHOTOPHORE_SKIN.get(), augmentSlot);
    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.tickCount % INTERVAL != 0 || !player.isInWater()) {
            return;
        }

        double radius = NTConfig.photophoreSkinRadius;
        List<LivingEntity> nearby = player.level().getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(radius), entity -> entity != player);

        for (LivingEntity entity : nearby) {
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, DURATION, 0, true, false, false));
        }
    }
}
