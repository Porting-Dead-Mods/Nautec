package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.network.SyncAugmentPayload;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
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
@EventBusSubscriber(modid = ModJam.MODID)
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
    public static void breakEvent(BlockEvent.BreakEvent event) {
        Iterable<Augment> augments = AugmentHelper.getAugments(event.getPlayer()).values();
        for (Augment augment : augments) {
            if (augment != null) {
                augment.breakBlock(event);
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
