package com.portingdeadmods.nautec.events;

import com.mojang.blaze3d.shaders.FogShape;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.client.screen.AugmentationViewerScreen;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.registries.NTKeybinds;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

public final class NTClientEvents {
    @EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static final class ClientInGameBus {
        @SubscribeEvent
        public static void onRenderFog(ViewportEvent.RenderFog event) {
            Entity cameraEntity = Minecraft.getInstance().cameraEntity;
            if (cameraEntity instanceof Player player) {
                if (cameraEntity.isUnderWater() && player.getItemBySlot(EquipmentSlot.HEAD).is(NTItems.DIVING_HELMET.get())) {
                    event.setNearPlaneDistance(-8.0f);
                    event.setFarPlaneDistance(250.0f);
                    event.setFogShape(FogShape.CYLINDER);
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();
            if (NTKeybinds.AUGMENT_SCREEN_KEYBIND.get().consumeClick()) {
                if (Minecraft.getInstance().screen == null || mc.player != null) {
                    if (!AugmentHelper.getAugments(mc.player).isEmpty())
                        Minecraft.getInstance().setScreen(new AugmentationViewerScreen(Component.literal("test"), Minecraft.getInstance().player));
                }
            }
        }
    }
}
