package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public record BacteriaMutation(Item catalyst, ResourceKey<Bacteria> bacteria, int chance) {
    public static Codec<BacteriaMutation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecUtils.ITEM_CODEC.fieldOf("catalyst").forGetter(BacteriaMutation::catalyst),
            ResourceKey.codec(NTRegistries.BACTERIA_KEY).fieldOf("bacteria").forGetter(BacteriaMutation::bacteria),
            Codec.INT.fieldOf("chance").forGetter(BacteriaMutation::chance)
    ).apply(instance, BacteriaMutation::new));

    public static StreamCodec<ByteBuf, BacteriaMutation> STREAM_CODEC = StreamCodec.composite(
            CodecUtils.ITEM_STREAM_CODEC,
            BacteriaMutation::catalyst,
            ResourceKey.streamCodec(NTRegistries.BACTERIA_KEY),
            BacteriaMutation::bacteria,
            ByteBufCodecs.INT,
            BacteriaMutation::chance,
            BacteriaMutation::new
    );
}
