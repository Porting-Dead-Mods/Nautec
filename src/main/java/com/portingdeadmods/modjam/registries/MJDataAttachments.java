package com.portingdeadmods.modjam.registries;

import com.mojang.serialization.Codec;
import com.portingdeadmods.modjam.ModJam;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class MJDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ModJam.MODID);

    public static final Supplier<AttachmentType<Integer>> HEAD_AUGMENTATION = ATTACHMENTS.register(
           "head_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> BODY_AUGMENTATION = ATTACHMENTS.register(
            "body_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> ARMS_AUGMENTATION = ATTACHMENTS.register(
            "arms_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> LEGS_AUGMENTATION = ATTACHMENTS.register(
            "legs_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> HEART_AUGMENTATION = ATTACHMENTS.register(
            "heart_augment_id", ()-> AttachmentType.<Integer>builder(()->0).serialize(Codec.INT).build()
    );
}
