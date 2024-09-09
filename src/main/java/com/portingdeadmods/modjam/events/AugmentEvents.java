package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import com.portingdeadmods.modjam.content.augments.StaticAugment;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.checkerframework.checker.units.qual.A;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = ModJam.MODID)
public class AugmentEvents {

    @SubscribeEvent
    public static void breakEvent(BlockEvent.BreakEvent event){
        StaticAugment[] augments = AugmentHelper.getAugments(event.getPlayer());
        for (int i = 0; i < augments.length; i++) {
            if (augments[i] != null){
                augments[i].breakBlock(Slot.GetValue(i),event);
            }
        }
    }


    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event){
        StaticAugment[] augments = AugmentHelper.getAugments(event.getEntity());
        for (int i = 0; i < 5; i++) {
            StaticAugment augment = augments[i];
            if (augment != null) {
                Slot slot = Slot.GetValue(i);
                Player player = event.getEntity();

                if (player.level().isClientSide) {
                    augment.clientTick(slot,event);
                    if (AugmentHelper.getCooldown(player, slot) >= 0){
                        AugmentHelper.setCooldownAndUpdate(player, slot, AugmentHelper.getCooldown(player, slot) - 1);
                    }
                } else {
                    augment.serverTick(slot,event);
                }
            }
        }
    }
}
