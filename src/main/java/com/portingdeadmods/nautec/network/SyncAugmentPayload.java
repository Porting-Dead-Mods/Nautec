package com.portingdeadmods.nautec.network;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.utils.AugmentClientHelper;
import com.portingdeadmods.nautec.utils.codec.AugmentCodecs;
import com.portingdeadmods.nautec.utils.AugmentHelper;
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

    public static final Type<SyncAugmentPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "augment_data_payload"));
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
            CompoundTag tag = payload.extraData();
            Player player = context.player();
            augment.setPlayer(player);
            augment.deserializeNBT(player.level().registryAccess(), tag);
            AugmentSlot augmentSlot = augment.getAugmentSlot();
            AugmentHelper.setAugment(player, augmentSlot, augment);
            AugmentHelper.setAugmentExtraData(player, augmentSlot, tag);
            if (player.level().isClientSide()) {
                AugmentClientHelper.invalidateCacheFor(player, augmentSlot);
            }
        });
    }
}
