package com.portingdeadmods.nautec.network;

import com.portingdeadmods.nautec.Nautec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Nautec.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkEvents {
    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Nautec.MODID);
        registrar.playToServer(
                KeyPressedPayload.TYPE,
                KeyPressedPayload.STREAM_CODEC,
                KeyPressedPayload::keyPressedAction
        );
        registrar.playToServer(
                BacteriaSlotClickedPayload.TYPE,
                BacteriaSlotClickedPayload.STREAM_CODEC,
                BacteriaSlotClickedPayload::handle
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
        registrar.playBidirectional(
                StartAugmentationPayload.TYPE,
                StartAugmentationPayload.STREAM_CODEC,
                StartAugmentationPayload::startAugmentation
        );
        registrar.playBidirectional(
                ClearAugmentPayload.TYPE,
                ClearAugmentPayload.STREAM_CODEC,
                ClearAugmentPayload::clearAugmentAction
        );
    }
}
