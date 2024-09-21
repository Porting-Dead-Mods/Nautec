package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.registries.MJAugments;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class GuardianEyeAugment extends Augment {
    public GuardianEyeAugment(AugmentSlot augmentSlot) {
        super(MJAugments.GUARDIAN_EYE_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_LALT)){
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot, augmentSlot.getSlotId()));
        }
    }

    @Override
    public void handleKeybindPress() {
        Vec3 look = player.getLookAngle();
        Vec3 startPos = player.getEyePosition(1.0f);
        double maxDistance = 15.0;
        double step = 0.1;
        for (double t = 0; t <= maxDistance; t += step) {
            Vec3 checkPos = startPos.add(look.scale(t));
            List<Entity> entities = player.level().getEntities(player, new AABB(checkPos.add(-0.5, -0.5, -0.5), checkPos.add(0.5, 0.5, 0.5)));
            for (Entity entity : entities) {
                if (entity != player && !(entity instanceof ItemEntity)) {
                    ModJam.LOGGER.info("Hit entity in line: {}", entity.getName().getString());
                    entity.hurt(entity.damageSources().magic(), 1f);
                    entity.setRemainingFireTicks(10);
                    return;
                }
            }
        }
    }

}
