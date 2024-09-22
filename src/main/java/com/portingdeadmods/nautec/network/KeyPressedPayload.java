package com.portingdeadmods.nautec.network;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.utils.AugmentCodecs;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record KeyPressedPayload(AugmentSlot augmentSlot) implements CustomPacketPayload {
    public static final Type<KeyPressedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "key_pressesd_paylad"));
    public static final StreamCodec<RegistryFriendlyByteBuf, KeyPressedPayload> STREAM_CODEC = StreamCodec.composite(
            AugmentCodecs.AUGMENT_SLOT_STREAM_CODEC,
            KeyPressedPayload::augmentSlot,
            KeyPressedPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void keyPressedAction(KeyPressedPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            AugmentHelper.getAugmentBySlot(player, payload.augmentSlot).handleKeybindPress();
        }).exceptionally(e -> {
            context.disconnect(Component.literal("action failed:  " + e.getMessage()));
            return null;
        });

    }
}
