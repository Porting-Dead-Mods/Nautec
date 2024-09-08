package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.system.linux.Stat;

public class GiveDiamondAugment extends Augment {
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        // ModJam.LOGGER.info("ClientTick for id {}", getId());
        if (InputUtils.isKeyDown(InputConstants.KEY_Y)) {
            PacketDistributor.sendToServer(new KeyPressedPayload(getId()));
        }
    }

    @Override
    public void handleKeybindPress(Player player) {
        player.addItem(Items.DIAMOND.getDefaultInstance());
    }
}
