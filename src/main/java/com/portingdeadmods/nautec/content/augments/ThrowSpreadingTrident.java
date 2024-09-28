package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.content.entites.ThrownBouncingTrident;
import com.portingdeadmods.nautec.content.entites.ThrownSpreadingTrident;
import com.portingdeadmods.nautec.network.KeyPressedPayload;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTKeybinds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class ThrowSpreadingTrident extends Augment {
    private static final float SPREAD_ANGLE = 8.0f;

    public ThrowSpreadingTrident(AugmentSlot augmentSlot) {
        super(NTAugments.SPREADING_TRIDENT_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (NTKeybinds.THROW_SPREADING_KEYBIND.get().consumeClick() && !isOnCooldown()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot));
            handleKeybindPress();
        }
    }

    @Override
    public void handleKeybindPress() {
        if (!player.level().isClientSide()) {
            ThrownSpreadingTrident trident = new ThrownSpreadingTrident(player.level(), player, Items.TRIDENT.getDefaultInstance(), 1);
            trident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 0.0f);
            player.level().addFreshEntity(trident);
        }
        setCooldown(20); // Set the cooldown, which decrements by 1 every tick
    }
}
