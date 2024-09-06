package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.Augment;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import com.portingdeadmods.modjam.content.augments.Augments;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = ModJam.MODID)
public class AugmentEvents {
    @SubscribeEvent
    public static void breakEvent(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        if(AugmentHelper.playerHasAugment(player, Slot.HEAD, Augments.TEST_AUGMENT)){
            Augment.onBreak(event);
        }
    }
}
