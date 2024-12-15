package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.content.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.content.bacteria.SimpleBacteria;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public interface Bacteria {
    Codec<Bacteria> CODEC = NTRegistries.BACTERIA_SERIALIZER.byNameCodec().dispatch(Bacteria::getSerializer, BacteriaSerializer::mapCodec);
    StreamCodec<RegistryFriendlyByteBuf, Bacteria> STREAM_CODEC = ByteBufCodecs.registry(NTRegistries.BACTERIA_SERIALIZER_KEY).dispatch(Bacteria::getSerializer, BacteriaSerializer::streamCodec);

    /*
    Bacteria! We begin with only one!
    Bacteria! Two's what we then become!
    Bacteria! Each of us becomes two more!
    Bacteria! We are stronger than before!
    Bacteria! We keep growing at this rate!
    Bacteria! No longer shall we wait!
    Bacteria! The plan now unfolds!
    Bacteria! We will take over the world!
     */
    ResourceLocation id();

    BacteriaStats stats();

    BacteriaSerializer<?> getSerializer();
}
