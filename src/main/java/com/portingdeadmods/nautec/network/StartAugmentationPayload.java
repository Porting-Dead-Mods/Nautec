package com.portingdeadmods.nautec.network;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.utils.codec.AugmentCodecs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record StartAugmentationPayload(BlockPos pos, AugmentSlot slot, UUID playerUUID) implements CustomPacketPayload {
    public static final Type<StartAugmentationPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "start_augmentation_payload"));

    public static final StreamCodec<RegistryFriendlyByteBuf, StartAugmentationPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            StartAugmentationPayload::pos,
            AugmentCodecs.AUGMENT_SLOT_STREAM_CODEC,
            StartAugmentationPayload::slot,
            UUIDUtil.STREAM_CODEC,
            StartAugmentationPayload::playerUUID,
            StartAugmentationPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void startAugmentation(StartAugmentationPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            BlockEntity be = level.getBlockEntity(payload.pos());
            if (be instanceof AugmentationStationBlockEntity asbe) {
                asbe.startAugmentation(level.getPlayerByUUID(payload.playerUUID), payload.slot());
            }
        });
    }
}
