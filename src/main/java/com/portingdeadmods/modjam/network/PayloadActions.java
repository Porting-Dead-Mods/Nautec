package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PayloadActions {

    public static void keyPressedAction(KeyPressedPayload payload, IPayloadContext context){
        context.enqueueWork(()->{
            Player player = context.player();
            int augmentId = payload.augmentId();

            // ModJam.LOGGER.info("Sent with packetId: {}", augmentId);
            AugmentHelper.getAugment(augmentId).handleKeybindPress(Slot.GetValue(payload.slot()), player);
        }).exceptionally(e->{
            context.disconnect(Component.literal("action failed:  "+ e.getMessage()));
            return null;
        });

    }
    public static void setAugmentDataAction(SetAugmentDataPayload payload, IPayloadContext context){
        context.enqueueWork(()->{
            AugmentHelper.setId(context.player(), Slot.GetValue(payload.slot()), payload.augmentId());
            // ModJam.LOGGER.debug("Syncing, id: {}",payload.augmentId());
        });
    }
    public static void setCooldownAction(SetCooldownPayload payload, IPayloadContext context){
        context.enqueueWork(()->{
           AugmentHelper.setCooldown(context.player(), Slot.GetValue(payload.slot()), payload.cooldown());
        });
    }
}
