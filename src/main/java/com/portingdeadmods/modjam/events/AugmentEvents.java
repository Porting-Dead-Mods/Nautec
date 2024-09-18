package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.content.augments.AugmentSlots;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = ModJam.MODID)
public class AugmentEvents {

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
    public static void playerTick(PlayerTickEvent.Post event){
        Iterable<Augment> augments = AugmentHelper.getAugments(event.getEntity()).values();
        for (Augment augment : augments) {
            if (augment != null) {
                AugmentSlot slot = augment.getAugmentSlot();
                Player player = event.getEntity();

                if (player.level().isClientSide) {
                    augment.clientTick(event);
                    // TODO: Move this to tick method of Augment class
                    if (augment.getCooldown() >= 0){
                        augment.setCooldown(augment.getCooldown() - 1);
                    }
                } else {
                    augment.serverTick(event);
                }
            }
        }
    }
}
