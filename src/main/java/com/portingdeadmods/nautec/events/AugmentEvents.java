package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.network.SyncAugmentPayload;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = Nautec.MODID)
public final class AugmentEvents {
    @SubscribeEvent
    public static void fallEvent(LivingFallEvent event) {
        if (event.getEntity() instanceof Player) {
            Iterable<Augment> augments = AugmentHelper.getAugments((Player) event.getEntity()).values();
            for (Augment augment : augments) {
                if (augment != null) {
                    augment.fall(event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void breakEvent(BlockEvent.BreakEvent event){
        Iterable<Augment> augments = AugmentHelper.getAugments(event.getPlayer()).values();
        for (Augment augment : augments) {
            if (augment != null) {
                //augments.get(i).breakBlock(AugmentSlot.GetValue(i),event);
            }
        }
    }


    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Iterable<Augment> augments = AugmentHelper.getAugments(player).values();
        for (Augment augment : augments) {
            if (augment != null) {
                AugmentSlot slot = augment.getAugmentSlot();
                augment.commonTick(event);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        Iterable<Augment> augments = AugmentHelper.getAugments(player).values();
        Map<AugmentSlot, CompoundTag> data = AugmentHelper.getAugmentsData(player);
        for (Augment augment : augments) {
            if (augment != null) {
                AugmentSlot slot = augment.getAugmentSlot();
                augment.setPlayer(player);
                CompoundTag nbt = data.get(slot);
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncAugmentPayload(augment, nbt != null ? nbt : new CompoundTag()));
            }
        }
    }
}
