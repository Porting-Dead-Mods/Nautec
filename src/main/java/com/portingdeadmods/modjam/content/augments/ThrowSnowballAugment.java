package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.registries.MJAugments;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class ThrowSnowballAugment extends Augment {
    public ThrowSnowballAugment(AugmentSlot augmentSlot) {
        super(MJAugments.THROW_SNOWBALL.get(), augmentSlot);
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
        Snowball snowball = new Snowball(player.level(), player);
        snowball.setItem(Items.SNOWBALL.getDefaultInstance());
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        player.level().addFreshEntity(snowball);
        setCooldown(20); // Set the cooldown, which decrements by 1 every tick
    }
}
