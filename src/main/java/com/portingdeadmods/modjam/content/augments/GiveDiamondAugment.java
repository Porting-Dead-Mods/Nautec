package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.registries.MJAugments;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class GiveDiamondAugment extends Augment {
    public GiveDiamondAugment(AugmentSlot augmentSlot) {
        super(MJAugments.GIVE_DIAMOND.get(), augmentSlot);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        ModJam.LOGGER.debug("Client ticking");
        if (InputUtils.isKeyDown(InputConstants.KEY_Y)) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot, augmentSlot.getSlotId()));
        }
    }

    @Override
    public void handleKeybindPress() {
        player.addItem(Items.DIAMOND.getDefaultInstance());
    }
}
