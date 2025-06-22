package com.portingdeadmods.nautec.network;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.utils.AugmentClientHelper;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import com.portingdeadmods.nautec.utils.codec.AugmentCodecs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record ClearAugmentPayload(AugmentSlot augmentSlot) implements CustomPacketPayload {

    public static final Type<ClearAugmentPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "clear_augment_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClearAugmentPayload> STREAM_CODEC = StreamCodec.composite(
            AugmentCodecs.AUGMENT_SLOT_STREAM_CODEC,
            ClearAugmentPayload::augmentSlot,
            ClearAugmentPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void clearAugmentAction(ClearAugmentPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            AugmentSlot slot = payload.augmentSlot();
            
            // Get current augments and remove the specified slot
            Map<AugmentSlot, Augment> augments = new HashMap<>(AugmentHelper.getAugments(player));
            Map<AugmentSlot, CompoundTag> augmentsData = new HashMap<>(AugmentHelper.getAugmentsData(player));
            
            augments.remove(slot);
            augmentsData.remove(slot);
            
            // Update player data
            player.setData(NTDataAttachments.AUGMENTS, augments);
            player.setData(NTDataAttachments.AUGMENTS_EXTRA_DATA, augmentsData);
            
            // Invalidate client cache if on client side
            if (player.level().isClientSide()) {
                AugmentClientHelper.invalidateCacheFor(player, slot);
            }
        });
    }
}