package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ThrowSnowballAugment extends Augment {
    @Override
    public int getId() {
        return 3;
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        // ModJam.LOGGER.info("ClientTick for id {}", getId());

        if (InputUtils.isKeyDown(InputConstants.KEY_Y)) {
            // ModJam.LOGGER.info("Snow");
            PacketDistributor.sendToServer(new KeyPressedPayload(getId()));
        }
    }

    @Override
    public void handleKeybindPress(Player player) {
        Snowball snowball = new Snowball(player.level(), player);
        snowball.setItem(Items.SNOWBALL.getDefaultInstance());
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        player.level().addFreshEntity(snowball);
    }
}
