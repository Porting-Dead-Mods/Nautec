package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.utils.ParticlesUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderMagnetAugment extends Augment {
    private static final double MAGNET_RADIUS = 5.0;
    private static final double MAGNET_PULL_SPEED = 0.2;

    public EnderMagnetAugment(AugmentSlot augmentSlot) {
        super(NTAugments.ENDER_MAGNET_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {
        if (!player.isCrouching()) {
            List<ItemEntity> nearbyItems = player.level().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(MAGNET_RADIUS));
            for (ItemEntity itemEntity : nearbyItems) {
                if (player.addItem(itemEntity.getItem())) {
                    itemEntity.remove(Entity.RemovalReason.DISCARDED);
                    // TODO: Send packet to clients to spawn particles and play sound when picked up
                }
            }
        }
    }
}
