package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.network.KeyPressedPayload;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTKeybinds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class GuardianEyeAugment extends Augment {
    public Vec3 laserFiredPos = null;
    public int timeLeft = 0;

    private Entity targetEntity;

    private float clientLaserTime;

    public GuardianEyeAugment(AugmentSlot augmentSlot) {
        super(NTAugments.GUARDIAN_EYE.get(), augmentSlot);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (NTKeybinds.ACTIVATE_LASER_KEYBIND.get().consumeClick()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot));
            handleKeybindPress();
        }

        if (targetEntity != null) {
            if (clientLaserTime < getLaserAnimTimeDuration()) {
                this.clientLaserTime += 0.5f;
            } else {
                this.clientLaserTime = 0;
            }

            double d5 = (double) this.getLaserScale(0.0F);
            double d0 = targetEntity.getX() - player.getX();
            double d1 = targetEntity.getY(0.5) - player.getEyeY();
            double d2 = targetEntity.getZ() - player.getZ();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d0 /= d3;
            d1 /= d3;
            d2 /= d3;
            double d4 = player.getRandom().nextDouble();

            while (d4 < d3) {
                d4 += 1.8 - d5 + player.getRandom().nextDouble() * (1.7 - d5);
                player.level()
                        .addParticle(ParticleTypes.BUBBLE, player.getX() + d0 * d4, player.getEyeY() + d1 * d4, player.getZ() + d2 * d4, 0.0, 0.0, 0.0);
            }

        }
    }

    public int getLaserAnimTimeDuration() {
        return 80;
    }

    public float getClientLaserTime() {
        return clientLaserTime;
    }

    public float getLaserScale(float partialTick) {
        return (this.clientLaserTime + partialTick) / (float) this.getLaserAnimTimeDuration();
    }

    @Override
    public void handleKeybindPress() {
        Vec3 look = player.getLookAngle();
        Vec3 startPos = player.getEyePosition(1.0f);
        double maxDistance = 15.0;
        double step = 0.1;
        for (double t = 0; t <= maxDistance; t += step) {
            Vec3 checkPos = startPos.add(look.scale(t));
            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(checkPos.add(-0.5, -0.5, -0.5), checkPos.add(0.5, 0.5, 0.5)));
            for (LivingEntity entity : entities) {
                if (entity != player) {
                    if (!player.level().isClientSide) {
                        // TODO: Make a config value for damage amoutn
                        entity.hurt(entity.damageSources().magic(), 4f);
                        entity.setRemainingFireTicks(10);
                        timeLeft = 1000;
                        laserFiredPos = entity.getEyePosition();
                    }
                    this.targetEntity = entity;
                    return;
                }
            }
        }
    }

    public Entity getTargetEntity() {
        return targetEntity;
    }
}
