package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import java.util.Map;

@EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT)
public final class AugmentClientEvents {
    // TODO: CACHING
    @SubscribeEvent
    public static void renderPlayerPart(RenderPlayerEvent.Pre event) {
        Map<AugmentSlot, Augment> augments = AugmentHelper.getAugments(event.getEntity());
        for (AugmentSlot slot : augments.keySet()) {
            Augment augment = augments.get(slot);
            if (augment.replaceBodyPart()) {
                ModelPart modelPart = slot.getModelPart().getModelPart(event.getRenderer().getModel());
                if (modelPart != null) {
                    modelPart.visible = false;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        PlayerRenderer renderer = event.getRenderer();
        renderer.addLayer(new AugmentLayerRenderer(renderer));
    }
}
