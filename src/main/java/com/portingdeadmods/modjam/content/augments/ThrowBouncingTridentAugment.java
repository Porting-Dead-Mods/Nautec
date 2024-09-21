package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.content.entites.ThrownBouncingTrident;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.registries.MJAugments;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class ThrowBouncingTridentAugment extends Augment {
    public ThrowBouncingTridentAugment(AugmentSlot augmentSlot) {
        super(MJAugments.THROWN_BOUNCING_TRIDENT_AUGMENT.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_Y) && !isOnCooldown()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot));
        }
    }

    @Override
    public void handleKeybindPress() {
        ThrownBouncingTrident trident = new ThrownBouncingTrident(player.level(),player,Items.TRIDENT.getDefaultInstance());
        trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 0.0f);
        player.level().addFreshEntity(trident);
        setCooldown(20); // Set the cooldown, which decrements by 1 every tick
    }
}
