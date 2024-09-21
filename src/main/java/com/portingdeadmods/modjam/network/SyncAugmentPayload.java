package com.portingdeadmods.modjam.network;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.utils.AugmentCodecs;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SyncAugmentPayload(Augment augment, CompoundTag extraData) implements CustomPacketPayload {

    public static final Type<SyncAugmentPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "augment_data_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncAugmentPayload> STREAM_CODEC = StreamCodec.composite(
            AugmentCodecs.AUGMENT_STREAM_CODEC,
            SyncAugmentPayload::augment,
            ByteBufCodecs.COMPOUND_TAG,
            SyncAugmentPayload::extraData,
            SyncAugmentPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void setAugmentDataAction(SyncAugmentPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Augment augment = payload.augment();
            Player player = context.player();
            augment.setPlayer(player);
            AugmentSlot augmentSlot = augment.getAugmentSlot();
            AugmentHelper.setAugment(player, augmentSlot, augment);
            AugmentHelper.setAugmentExtraData(player, augmentSlot, payload.extraData());
        });
    }
}
