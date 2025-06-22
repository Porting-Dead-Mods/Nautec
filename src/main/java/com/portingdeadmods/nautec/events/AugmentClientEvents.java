package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.client.renderer.augments.helper.GuardianEyeRenderHelper;
import com.portingdeadmods.nautec.content.augments.GuardianEyeAugment;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.Map;

@EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT)
public final class AugmentClientEvents {

    @SubscribeEvent
    public static void renderPlayerPart(RenderPlayerEvent.Pre event) {
        AugmentSlotsRenderer.render(event);
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        // Needs to be outside the augment renderer cuz pose stacks
        Map<AugmentSlot, Augment> augments = AugmentLayerRenderer.AUGMENTS_CACHE;
        for (Augment augment : augments.values()) {
            if (augment != null && augment instanceof GuardianEyeAugment eyeAugment && eyeAugment.getTargetEntity() != null) {
                GuardianEyeRenderHelper.render(event.getEntity(), eyeAugment, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource());
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        // Clear augment cache when disconnecting from a server or leaving a world
        AugmentLayerRenderer.AUGMENTS_CACHE.clear();
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        // Clear augment cache when unloading a level (world change)
        if (event.getLevel().isClientSide()) {
            AugmentLayerRenderer.AUGMENTS_CACHE.clear();
        }
    }
}
