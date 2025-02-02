package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

/**
 * Bacteria! We begin with only one!
 * Bacteria! Two's what we then become!
 * Bacteria! Each of us becomes two more!
 * Bacteria! We are stronger than before!
 * Bacteria! We keep growing at this rate!
 * Bacteria! No longer shall we wait!
 * Bacteria! The plan now unfolds!
 * Bacteria! We will take over the world!
 */
public interface Bacteria {
    Codec<ResourceKey<Bacteria>> BACTERIA_TYPE_CODEC = ResourceKey.codec(NTRegistries.BACTERIA_KEY);
    StreamCodec<ByteBuf, ResourceKey<Bacteria>> BACTERIA_TYPE_STREAM_CODEC = ResourceKey.streamCodec(NTRegistries.BACTERIA_KEY);

    Codec<Bacteria> CODEC = NTRegistries.BACTERIA_SERIALIZER.byNameCodec().dispatch(Bacteria::getSerializer, BacteriaSerializer::mapCodec);
    StreamCodec<RegistryFriendlyByteBuf, Bacteria> STREAM_CODEC = ByteBufCodecs.registry(NTRegistries.BACTERIA_SERIALIZER_KEY).dispatch(Bacteria::getSerializer, BacteriaSerializer::streamCodec);

    Resource resource();

    BacteriaStats<?> stats();

    BacteriaSerializer<?> getSerializer();

    interface Resource {
        Codec<? extends Resource> codec();

        StreamCodec<ByteBuf, ? extends Resource> streamCodec();

        record ItemResource(Item item) implements Resource {
            public static final Codec<ItemResource> CODEC = CodecUtils.ITEM_CODEC.xmap(ItemResource::new, ItemResource::item);
            public static final StreamCodec<ByteBuf, ItemResource> STREAM_CODEC = CodecUtils.ITEM_STREAM_CODEC.map(ItemResource::new, ItemResource::item);

            @Override
            public Codec<ItemResource> codec() {
                return CODEC;
            }

            @Override
            public StreamCodec<ByteBuf, ItemResource> streamCodec() {
                return STREAM_CODEC;
            }
        }
    }

    interface Builder<T extends Bacteria> {
        T build();
    }
}
