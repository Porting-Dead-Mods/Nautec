package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(modid = ModJam.MODID, value = Dist.CLIENT)
public final class AugmentClientEvents {
    @SubscribeEvent
    public static void renderPlayerLegs(RenderPlayerEvent.Pre event) {
        event.getRenderer().getModel().body.visible = false;
    }
}
