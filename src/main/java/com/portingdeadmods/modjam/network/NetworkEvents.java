package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ModJam.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {
    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ModJam.MODID);
        registrar.playToServer(
                KeyPressedPayload.TYPE,
                KeyPressedPayload.STREAM_CODEC,
                KeyPressedPayload::keyPressedAction
        );
        registrar.playBidirectional(
                SyncAugmentPayload.TYPE,
                SyncAugmentPayload.STREAM_CODEC,
                SyncAugmentPayload::setAugmentDataAction
        );
        registrar.playBidirectional(
                SetCooldownPayload.TYPE,
                SetCooldownPayload.STREAM_CODEC,
                SetCooldownPayload::setCooldownAction
        );
    }
}
