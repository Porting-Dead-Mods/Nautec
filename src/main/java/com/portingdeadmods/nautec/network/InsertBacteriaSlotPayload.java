package com.portingdeadmods.nautec.network;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.NTDataMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InsertBacteriaSlotPayload(BlockPos pos, int slot,
                                        BacteriaInstance bacteria) implements CustomPacketPayload {
    public static final Type<InsertBacteriaSlotPayload> TYPE = new Type<>(Nautec.rl("insert_bacteria"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InsertBacteriaSlotPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            InsertBacteriaSlotPayload::pos,
            ByteBufCodecs.INT,
            InsertBacteriaSlotPayload::slot,
            BacteriaInstance.STREAM_CODEC,
            InsertBacteriaSlotPayload::bacteria,
            InsertBacteriaSlotPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Level level = player.level();
            IBacteriaStorage itemBacteriaStorage = player.containerMenu.getCarried().getCapability(NTCapabilities.BacteriaStorage.ITEM);
            if (level.getBlockEntity(pos) instanceof ContainerBlockEntity be) {
                IBacteriaStorage beBacteriaStorage = be.getBacteriaStorage();
                if (itemBacteriaStorage.getBacteria(0).isEmpty()
                        || BacteriaInstance.isSameBacteriaAndStats(itemBacteriaStorage.getBacteria(0), beBacteriaStorage.getBacteria(slot))) {
                    BacteriaInstance instance = beBacteriaStorage.getBacteria(slot).copy();
                    itemBacteriaStorage.setBacteria(0, instance);
                    itemBacteriaStorage.onBacteriaChanged(slot);
                    beBacteriaStorage.setBacteria(slot, BacteriaInstance.EMPTY.copy());
                    beBacteriaStorage.onBacteriaChanged(slot);
                } else {
                    beBacteriaStorage.insertBacteria(slot, bacteria);
                    itemBacteriaStorage.setBacteria(0, BacteriaInstance.EMPTY.copy());
                    itemBacteriaStorage.onBacteriaChanged(slot);
                }
            }
        }).exceptionally(err -> {
            Nautec.LOGGER.error("Error handling bacteria payload", err);
            return null;
        });
    }
}
