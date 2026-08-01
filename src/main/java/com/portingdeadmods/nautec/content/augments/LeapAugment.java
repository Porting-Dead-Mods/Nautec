package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTKeybinds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.portingdeadmods.nautec.utils.MathUtils.*;

public class LeapAugment extends Augment {
    public LeapAugment(AugmentSlot augmentSlot) {
        super(NTAugments.LEAP_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (NTKeybinds.LEAP_KEYBIND.get().consumeClick() && !isOnCooldown() && event.getEntity().onGround()) {
            // Not to be sent to server, movement is on the client apparently
            handleKeybindPress();
        }
    }

    @Override
    public void handleKeybindPress() {
        Vec3 lookVec = player.getLookAngle();
        float magnitude = 1.8f;
        Vec3 leapVector = new Vec3(lookVec.x, lookVec.y, lookVec.z);
        float initialYaw = (float) Math.atan2(leapVector.z, leapVector.x);
        leapVector = rotateYaw(leapVector, initialYaw);
        double leapPitch = Math.toDegrees(Math.asin(leapVector.y / leapVector.length()));
        if (leapPitch > 80) {
            leapVector = new Vec3(0.0d, 1.0d, 0.0d);
            leapPitch = 90.0d;
        } else {
            leapVector = rotateRoll(leapVector, (float) Math.toRadians(-10.0d));
            leapVector = rotateYaw(leapVector, -initialYaw);
            leapVector = leapVector.normalize();
        }
        double coefficient = 1.6d - map(Math.abs(leapPitch), 0.0d, 90.0d, 0.6d, 1.0d);
        leapVector = leapVector.scale(magnitude * coefficient);
        player.setDeltaMovement(leapVector);
        player.needsSync = true;
        setCooldown(25);
        player.playSound(SoundEvents.UI_TOAST_IN, 2.0f, 1.0f);
        player.playSound(SoundEvents.UI_TOAST_IN, 1.5f, .8f);
    }
}