package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.registries.MJAugments;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

public class LeapAugment extends Augment {
    public LeapAugment(AugmentSlot augmentSlot) {
        super(MJAugments.LEAP_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_LALT) && !isOnCooldown() && event.getEntity().onGround()) {
            // Not to be sent to server, movement is on the client apparently
            // PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot, augmentSlot.getSlotId()));
            handleKeybindPress();
        }
    }

    @Override
    public void handleKeybindPress() {
        // Vec3 leapVec = new Vec3(0,1,0);
        // player.setDeltaMovement(leapVec);
        // player.hasImpulse = true;
        // ModJam.LOGGER.info("Testing leap movement: " + leapVec);
        // It is just maths bro
        Vec3 lookVec = player.getLookAngle();
        float magnitude = 1.8f;
        Vec3 leapVector = new Vec3(lookVec.x, lookVec.y, lookVec.z);
        float initialYaw = (float) Math.atan2(leapVector.z, leapVector.x);
        leapVector = rotateYaw(leapVector, initialYaw);
        double leapPitch = Math.toDegrees(Math.asin(leapVector.y / leapVector.length()));
        // I guess it can't  really go much above 90 but there you go
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
        // ModJam.LOGGER.info(player.getDeltaMovement().toString());
        player.setDeltaMovement(leapVector);
        // ModJam.LOGGER.info(player.getDeltaMovement().toString());
        player.hasImpulse = true;
        setCooldown(25);
        // Look at the sick graph omg
    }

    public Vec3 rotateYaw(Vec3 vec, float yaw) {
        float fc = Mth.cos(yaw);
        float fs = Mth.sin(yaw);
        return new Vec3(vec.x * fc + vec.z * fs, vec.y, vec.z * fc - vec.x * fs);
    }

    public Vec3 rotateRoll(Vec3 vec, float roll) {
        float fc = Mth.cos(roll);
        float fs = Mth.sin(roll);
        return new Vec3(vec.x * fc + vec.y * fs, vec.y * fc - vec.x * fs, vec.z);
    }

    public double map(double val, double inMin, double inMax, double desMin, double desMax) {
        return desMin + (desMax - desMin) * (val - inMin) / (inMax - inMin);
    }
}
// Wow this class is full of maths, I guess the person who did it, is just good at maths :shrug:
