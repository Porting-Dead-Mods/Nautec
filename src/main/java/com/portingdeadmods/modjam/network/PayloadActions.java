package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PayloadActions {
    public static void keyPressedAction(KeyPressedPayload payload, IPayloadContext context){
        ModJam.LOGGER.debug("Key pressed");
        context.enqueueWork(()->{
            Player player = context.player();
            int augmentId = payload.augmentId();
            ModJam.LOGGER.info("Sent with packetId: {}", augmentId);
            AugmentHelper.getAugment(augmentId).handleKeybindPress(player);
        });
    }
    public static void augmentDataAction(AugmentDataPayload payload, IPayloadContext context){
        context.enqueueWork(()->{
            ModJam.LOGGER.info("Syncing data {}", payload.slot());
        });

    }
}
