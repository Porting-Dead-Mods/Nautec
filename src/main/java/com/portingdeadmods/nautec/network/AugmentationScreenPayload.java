package com.portingdeadmods.nautec.network;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.client.screen.AugmentScreen;
import com.portingdeadmods.nautec.content.menus.AugmentMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AugmentationScreenPayload(byte payloadType) implements CustomPacketPayload {
    // 0 -> toServer
    // 1 -> toClient
    public static final Type<AugmentationScreenPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "augmentation_screen_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AugmentationScreenPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            AugmentationScreenPayload::payloadType,
            AugmentationScreenPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void AugmentationScreenAction(AugmentationScreenPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (payload.payloadType == 0) {
                Player player = context.player();
                ServerPlayer serverPlayer = (ServerPlayer) player;
                Nautec.LOGGER.info("Processing key press on Server");
                //More logic here
                PacketDistributor.sendToPlayer(serverPlayer, new AugmentationScreenPayload((byte) 1));
            }

            if (payload.payloadType == 1) {
                Player player = context.player();
                Minecraft.getInstance().setScreen(new AugmentScreen(
                        new AugmentMenu(player, player.containerMenu.containerId),
                        player.getInventory(),
                        Component.literal("Augmentation")
                    )
                );

            }
        });
    }
}
