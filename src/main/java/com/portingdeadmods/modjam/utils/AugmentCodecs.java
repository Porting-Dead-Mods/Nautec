package com.portingdeadmods.modjam.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public final class AugmentCodecs {
    public static final Codec<AugmentSlot> AUGMENT_SLOT_CODEC =
            ResourceLocation.CODEC.xmap(MJRegistries.AUGMENT_SLOT::get, MJRegistries.AUGMENT_SLOT::getKey);
    public static final Codec<AugmentType<?>> AUGMENT_TYPE_CODEC =
            ResourceLocation.CODEC.xmap(MJRegistries.AUGMENT_TYPE::get, MJRegistries.AUGMENT_TYPE::getKey);
    public static final Codec<Augment> AUGMENT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
            AUGMENT_TYPE_CODEC.fieldOf("type").forGetter(Augment::getAugmentType),
            AUGMENT_SLOT_CODEC.fieldOf("slot").forGetter(Augment::getAugmentSlot)
    ).apply(builder, AugmentType::create));
    public static final Codec<Map<AugmentSlot, Augment>> AUGMENTS_CODEC = Codec.unboundedMap(AUGMENT_SLOT_CODEC, AUGMENT_CODEC);
    public static final Codec<Map<AugmentSlot, CompoundTag>> AUGMENTS_EXTRA_DATA_CODEC = Codec.unboundedMap(AUGMENT_SLOT_CODEC, CompoundTag.CODEC);

    public static final StreamCodec<ByteBuf, AugmentSlot> AUGMENT_SLOT_STREAM_CODEC =
            ByteBufCodecs.INT.map(MJRegistries.AUGMENT_SLOT::byId, MJRegistries.AUGMENT_SLOT::getId);
}