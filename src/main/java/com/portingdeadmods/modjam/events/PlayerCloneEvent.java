package com.portingdeadmods.modjam.events;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.registries.MJDataAttachments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.theillusivec4.curios.api.type.data.IEntitiesData;

import java.util.function.Supplier;

@EventBusSubscriber(modid = ModJam.MODID)
public class PlayerCloneEvent {
    private static void copyPlayerAttachment(PlayerEvent.Clone event, Supplier<AttachmentType<Integer>> attachment){
        if (event.isWasDeath() && event.getOriginal().hasData(attachment)){
            event.getEntity().setData(attachment, event.getOriginal().getData(attachment));
        }
    }
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event){
        copyPlayerAttachment(event, MJDataAttachments.HEAD_AUGMENTATION);
        copyPlayerAttachment(event, MJDataAttachments.BODY_AUGMENTATION);
        copyPlayerAttachment(event, MJDataAttachments.ARMS_AUGMENTATION);
        copyPlayerAttachment(event, MJDataAttachments.LEGS_AUGMENTATION);
        copyPlayerAttachment(event, MJDataAttachments.HEART_AUGMENTATION);
    }
}
