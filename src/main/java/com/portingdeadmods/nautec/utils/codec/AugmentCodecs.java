package com.portingdeadmods.nautec.utils.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.Map;

public final class AugmentCodecs {
    public static final Codec<AugmentSlot> AUGMENT_SLOT_CODEC = Identifier.CODEC.flatXmap(
            id -> {
                AugmentSlot slot = NTRegistries.AUGMENT_SLOT.getValue(id);
                return slot != null
                        ? DataResult.success(slot)
                        : DataResult.error(() -> "Unknown augment slot: " + id);
            },
            slot -> {
                Identifier id = NTRegistries.AUGMENT_SLOT.getKey(slot);
                return id != null
                        ? DataResult.success(id)
                        : DataResult.error(() -> "Unregistered augment slot instance: " + slot);
            }
    );
    public static final Codec<AugmentType<?>> AUGMENT_TYPE_CODEC = Identifier.CODEC.flatXmap(
            id -> {
                AugmentType<?> type = NTRegistries.AUGMENT_TYPE.getValue(id);
                return type != null
                        ? DataResult.success(type)
                        : DataResult.error(() -> "Unknown augment type: " + id);
            },
            type -> {
                Identifier id = NTRegistries.AUGMENT_TYPE.getKey(type);
                return id != null
                        ? DataResult.success(id)
                        : DataResult.error(() -> "Unregistered augment type instance: " + type);
            }
    );
    public static final Codec<Augment> AUGMENT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
            AUGMENT_TYPE_CODEC.fieldOf("type").forGetter(Augment::getAugmentType),
            AUGMENT_SLOT_CODEC.fieldOf("slot").forGetter(Augment::getAugmentSlot)
    ).apply(builder, AugmentType::create));
    public static final Codec<Map<AugmentSlot, Augment>> AUGMENTS_CODEC = Codec.unboundedMap(AUGMENT_SLOT_CODEC, AUGMENT_CODEC);
    public static final Codec<Map<AugmentSlot, CompoundTag>> AUGMENTS_EXTRA_DATA_CODEC = Codec.unboundedMap(AUGMENT_SLOT_CODEC, CompoundTag.CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, AugmentSlot> AUGMENT_SLOT_STREAM_CODEC =
            ByteBufCodecs.registry(NTRegistries.AUGMENT_SLOT_KEY);
    public static final StreamCodec<RegistryFriendlyByteBuf, AugmentType<?>> AUGMENT_TYPE_STREAM_CODEC =
            ByteBufCodecs.registry(NTRegistries.AUGMENT_TYPE_KEY);
    public static final StreamCodec<RegistryFriendlyByteBuf, Augment> AUGMENT_STREAM_CODEC = StreamCodec.composite(
            AUGMENT_TYPE_STREAM_CODEC,
            Augment::getAugmentType,
            AUGMENT_SLOT_STREAM_CODEC,
            Augment::getAugmentSlot,
            AugmentType::create
    );
}
