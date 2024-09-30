package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.client.renderer.augments.ModelPartGetter;
import com.portingdeadmods.nautec.client.renderer.augments.helper.GuardianEyeRenderHelper;
import com.portingdeadmods.nautec.content.augments.GuardianEyeAugment;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT)
public final class AugmentClientEvents {

    // TODO: CACHING
    @SubscribeEvent
    public static void renderPlayerPart(RenderPlayerEvent.Pre event) {
        AugmentSlotsRenderer.render(event);
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        PlayerRenderer renderer = event.getRenderer();
        renderer.addLayer(new AugmentLayerRenderer(renderer));
        // Needs to be outside the augment renderer cuz pose stacks
        Map<AugmentSlot, Augment> augments = AugmentHelper.getAugments(event.getEntity());
        for (Augment augment : augments.values()) {
            if (augment instanceof GuardianEyeAugment eyeAugment && eyeAugment.getTargetEntity() != null) {
                GuardianEyeRenderHelper.render(event.getEntity(), eyeAugment, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource());
            }
        }
    }
}
