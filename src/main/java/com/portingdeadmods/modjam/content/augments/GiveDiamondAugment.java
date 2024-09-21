package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.events.MJClientEvents;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.registries.MJAugments;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class GiveDiamondAugment extends Augment {
    public GiveDiamondAugment(AugmentSlot augmentSlot) {
        super(MJAugments.GIVE_DIAMOND.get(), augmentSlot);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (MJClientEvents.ClientBus.GIVE_DIAMOND_KEYMAP.get().consumeClick()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot, augmentSlot.getSlotId()));
        }
    }

    @Override
    public void handleKeybindPress() {
        player.addItem(Items.DIAMOND.getDefaultInstance());
    }
}
