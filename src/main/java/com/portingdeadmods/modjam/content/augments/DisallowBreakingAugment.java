package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.events.MJClientEvents;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class DisallowBreakingAugment extends Augment{
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void breakBlock(BlockEvent.BreakEvent event) {
        event.setCanceled(true);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        ModJam.LOGGER.info("ClientTick for id {}", getId());

        if (MJClientEvents.ClientBus.AUGMENT_TEST_KEYMAP.get().consumeClick()){
            ModJam.LOGGER.debug("Key down");
            PacketDistributor.sendToServer(new KeyPressedPayload(getId()));
        }
    }

    @Override
    public void handleKeybindPress(Player player) {
        player.addItem(Items.EMERALD.getDefaultInstance());
    }
}
