package com.portingdeadmods.nautec.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.content.entites.ThrownBouncingTrident;
import com.portingdeadmods.nautec.network.KeyPressedPayload;
import com.portingdeadmods.nautec.registries.NTAugmentSlots;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTKeybinds;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import com.portingdeadmods.nautec.utils.InputUtils;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

// TODO: Implement this
public class ThrowBouncingTridentAugment extends Augment {
    public ThrowBouncingTridentAugment(AugmentSlot augmentSlot) {
        super(NTAugments.THROWN_BOUNCING_TRIDENT_AUGMENT.get(), augmentSlot);
    }

    @Override
    public boolean replaceBodyPart() {
        return true;
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (NTKeybinds.THROW_TRIDENT_KEYBIND.get().consumeClick() && !isOnCooldown()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot));
            handleKeybindPress();
        }
    }

    @Override
    public void handleKeybindPress() {
        if (!player.level().isClientSide()) {
            ThrownBouncingTrident trident = new ThrownBouncingTrident(player.level(), player, Items.TRIDENT.getDefaultInstance(), 1);
            trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 0.0f);
            player.level().addFreshEntity(trident);
        }
        setCooldown(20); // Set the cooldown, which decrements by 1 every tick
    }
}
