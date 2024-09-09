package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record KeyPressedPayload(int augmentId, int slot) implements CustomPacketPayload {
    public static final Type<KeyPressedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "key_pressesd_paylad"));
    public static final StreamCodec<RegistryFriendlyByteBuf, KeyPressedPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            KeyPressedPayload::augmentId,
            ByteBufCodecs.INT,
            KeyPressedPayload::slot,
            KeyPressedPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void keyPressedAction(KeyPressedPayload payload, IPayloadContext context){
        context.enqueueWork(()->{
            Player player = context.player();
            int augmentId = payload.augmentId();
            AugmentHelper.getAugment(augmentId).handleKeybindPress(Slot.GetValue(payload.slot()), player);
        }).exceptionally(e->{
            context.disconnect(Component.literal("action failed:  "+ e.getMessage()));
            return null;
        });

    }
}
