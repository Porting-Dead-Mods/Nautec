package com.portingdeadmods.nautec.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.content.entites.ThrownBouncingTrident;
import com.portingdeadmods.nautec.network.KeyPressedPayload;
import com.portingdeadmods.nautec.registries.NTAugmentSlots;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.utils.InputUtils;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class ThrowBouncingTridentAugment extends Augment {
    public ThrowBouncingTridentAugment(AugmentSlot augmentSlot) {
        super(NTAugments.THROWN_BOUNCING_TRIDENT_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[] {
                NTAugmentSlots.LEFT_ARM.get(),
                NTAugmentSlots.RIGHT_ARM.get(),
        };
    }

    @Override
    public boolean replaceBodyPart() {
        return true;
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_Y) && !isOnCooldown()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot));
        }
    }

    @Override
    public void handleKeybindPress() {
        ThrownBouncingTrident trident = new ThrownBouncingTrident(player.level(),player,Items.TRIDENT.getDefaultInstance(), 1);
        trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 0.0f);
        player.level().addFreshEntity(trident);
        setCooldown(20); // Set the cooldown, which decrements by 1 every tick
    }
}
