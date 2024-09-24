package com.portingdeadmods.nautec.events;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.network.SyncAugmentPayload;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
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

                // Step-up
                AttributeInstance step_height = player.getAttribute(Attributes.STEP_HEIGHT);
                if (augment.getAugmentType() == NTAugments.STEP_UP_AUGMENT.get()) {
                    step_height.setBaseValue(1.0);
                } else {
                    step_height.setBaseValue(0.6f);
                }

                // Underwater Mining Speed
                AttributeInstance underwater_mining_speed = player.getAttribute(Attributes.SUBMERGED_MINING_SPEED);
                if (augment.getAugmentType() == NTAugments.UNDERWATER_MINING_SPEED_AUGMENT.get()) {
                    underwater_mining_speed.setBaseValue(1f);
                } else {
                    underwater_mining_speed.setBaseValue(0.1f);
                }

                // Bonus Hearts
                AttributeInstance max_health = player.getAttribute(Attributes.MAX_HEALTH);
                if (augment.getAugmentType() == NTAugments.BONUS_HEART_AUGMENT.get()) {
                    max_health.setBaseValue(40);
                } else {
                    max_health.setBaseValue(20);
                }

                // Fall Damage
                AttributeInstance fall_damage_multiply = player.getAttribute(Attributes.FALL_DAMAGE_MULTIPLIER);
                if (augment.getAugmentType() == NTAugments.PREVENT_FALL_DAMAGE_AUGMENT.get()) {
                    fall_damage_multiply.setBaseValue(0);
                } else {
                    fall_damage_multiply.setBaseValue(1);
                }
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
