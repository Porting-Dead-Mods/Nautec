package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.client.renderer.augments.helper.GuardianEyeRenderHelper;
import com.portingdeadmods.nautec.content.augments.GuardianEyeAugment;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.Map;

@EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT)
public final class AugmentClientEvents {

    @SubscribeEvent
    public static void renderPlayerPart(RenderPlayerEvent.Pre<?> event) {
        AugmentSlotsRenderer.render(event);
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post<?> event) {
        // Needs to be outside the augment renderer cuz pose stacks
        Map<AugmentSlot, Augment> augments = AugmentLayerRenderer.AUGMENTS_CACHE;
        for (Augment augment : augments.values()) {
            if (augment != null && augment instanceof GuardianEyeAugment eyeAugment && eyeAugment.getTargetEntity() != null) {
                GuardianEyeRenderHelper.render(eyeAugment.getPlayer(), eyeAugment, event.getPartialTick(), event.getPoseStack(), event.getSubmitNodeCollector());
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        AugmentLayerRenderer.AUGMENTS_CACHE.clear();
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide()) {
            AugmentLayerRenderer.AUGMENTS_CACHE.clear();
        }
    }
}
