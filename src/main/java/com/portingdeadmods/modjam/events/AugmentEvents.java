package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import com.portingdeadmods.modjam.content.augments.StaticAugment;
import com.portingdeadmods.modjam.network.AugmentDataPayload;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = ModJam.MODID)
public class AugmentEvents {
    public static void logInEvent(PlayerEvent.PlayerLoggedInEvent event){
        if (event.getEntity().level().isClientSide) return;
        PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new AugmentDataPayload(1,1));
    }

    @SubscribeEvent
    public static void breakEvent(BlockEvent.BreakEvent event){
        StaticAugment[] augments = AugmentHelper.getAugments(event.getPlayer());
        for (int i = 0; i < augments.length; i++) {
            if (augments[i] != null){
                augments[i].breakBlock(event);
            }
        }
    }


    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event){
        StaticAugment[] augments = AugmentHelper.getAugments(event.getEntity());
        for (int i = 0; i < augments.length; i++) {
            if (augments[i] != null){
                if (event.getEntity().level().isClientSide){
                    augments[i].clientTick(event);
                } else {
                    augments[i].serverTick(event);
                }
            }
        }
    }
}
