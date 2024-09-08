package com.portingdeadmods.modjam.registries;

import com.mojang.serialization.Codec;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.utils.CodecUtils;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public final class MJDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ModJam.MODID);

    public static final Supplier<AttachmentType<Integer>> HEAD_AUGMENTATION = ATTACHMENTS.register(
           "head_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> HEAD_AUGMENTATION_COOLDOWN = ATTACHMENTS.register(
           "head_augment_cooldown", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> HEAD_AUGMENTATION_DATA = ATTACHMENTS.register(
           "head_augment_data", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> BODY_AUGMENTATION = ATTACHMENTS.register(
            "body_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> BODY_AUGMENTATION_COOLDOWN = ATTACHMENTS.register(
            "body_augment_cooldown", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> BODY_AUGMENTATION_DATA = ATTACHMENTS.register(
            "body_augment_data", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> ARMS_AUGMENTATION = ATTACHMENTS.register(
            "arms_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> ARMS_AUGMENTATION_COOLDOWN = ATTACHMENTS.register(
            "arms_augment_cooldown", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> ARMS_AUGMENTATION_DATA = ATTACHMENTS.register(
            "arms_augment_data", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> LEGS_AUGMENTATION = ATTACHMENTS.register(
            "legs_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> LEGS_AUGMENTATION_COOLDOWN = ATTACHMENTS.register(
            "legs_augment_cooldown", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> LEGS_AUGMENTATION_DATA = ATTACHMENTS.register(
            "legs_augment_data", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> HEART_AUGMENTATION = ATTACHMENTS.register(
            "heart_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> HEART_AUGMENTATION_COOLDOWN = ATTACHMENTS.register(
            "heart_augment_cooldown", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> HEART_AUGMENTATION_DATA = ATTACHMENTS.register(
            "heart_augment_data", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).copyOnDeath().build()
    );

    // Lasers in a chunk
    public static final Supplier<AttachmentType<Set<BlockPos>>> CHUNK_LASERS = ATTACHMENTS.register(
            "chunk_lasers", ()-> AttachmentType.<Set<BlockPos>>builder(()->Set.of()).serialize(CodecUtils.set(BlockPos.CODEC)).build()
    );
}
