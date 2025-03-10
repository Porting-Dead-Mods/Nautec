package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class MagnetAugment extends Augment {
    private static final double MAGNET_RADIUS = 5.0;
    private static final double MAGNET_PULL_SPEED = 0.2;

    public MagnetAugment(AugmentSlot augmentSlot) {
        super(NTAugments.MAGNET_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void serverTick(PlayerTickEvent.Post event) {
        if (!player.isCrouching()) {
            List<ItemEntity> nearbyItems = player.level().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(MAGNET_RADIUS));
            for (ItemEntity itemEntity : nearbyItems) {
                Vec3 pullVec = player.position().subtract(itemEntity.position()).normalize().scale(MAGNET_PULL_SPEED);
                itemEntity.setPickUpDelay(0);
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(pullVec));
            }
        }
    }
}
