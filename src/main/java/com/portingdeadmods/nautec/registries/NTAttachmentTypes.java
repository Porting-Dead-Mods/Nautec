package com.portingdeadmods.nautec.registries;

import com.mojang.serialization.Codec;
import com.portingdeadmods.nautec.Nautec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class NTAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Nautec.MODID);

    public static final Supplier<AttachmentType<Boolean>> HAS_NAUTEC_GUIDE = ATTACHMENT_TYPES.register(
            "has_nautec_guide", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).copyOnDeath().build());
}
