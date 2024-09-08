package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SetAugmentDataPayload(int augmentId, int slot) implements CustomPacketPayload {

    public static final Type<SetAugmentDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "augment_data_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetAugmentDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetAugmentDataPayload::augmentId,
            ByteBufCodecs.INT,
            SetAugmentDataPayload::slot,
            SetAugmentDataPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
