package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetCooldownPayload (int cooldown, int slot)implements CustomPacketPayload {
    public static final Type<SetCooldownPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "augment_cooldown_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetCooldownPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetCooldownPayload::cooldown,
            ByteBufCodecs.INT,
            SetCooldownPayload::slot,
            SetCooldownPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
