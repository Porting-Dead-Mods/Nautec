package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT)
public final class AugmentClientEvents {
    @SubscribeEvent
    public static void renderPlayerLegs(RenderPlayerEvent.Pre event) {
    }
}
