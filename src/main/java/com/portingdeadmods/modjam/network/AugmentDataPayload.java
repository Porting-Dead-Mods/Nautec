package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record AugmentDataPayload(int augmentId, int slot) implements CustomPacketPayload {

    public static final Type<AugmentDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "augment_data_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AugmentDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            AugmentDataPayload::augmentId,
            ByteBufCodecs.VAR_INT,
            AugmentDataPayload::slot,
            AugmentDataPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
