package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.utils.InputUtils;
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
    public void breakBlock(Slot slot, BlockEvent.BreakEvent event) {
        event.setCanceled(true);
    }

    @Override
    public void clientTick(Slot slot, PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_Y)){
            PacketDistributor.sendToServer(new KeyPressedPayload(getId(),slot.slotId));
        }
    }

    @Override
    public void handleKeybindPress(Slot slot, Player player) {
        player.addItem(Items.EMERALD.getDefaultInstance());
    }
}
