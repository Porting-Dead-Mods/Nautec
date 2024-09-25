package com.portingdeadmods.nautec.data;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.utils.AugmentCodecs;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public final class NTDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Nautec.MODID);

    public static final Supplier<AttachmentType<Map<AugmentSlot, Augment>>> AUGMENTS = ATTACHMENTS.register(
            "ingredients", () -> AttachmentType.<Map<AugmentSlot, Augment>>builder(Collections::emptyMap)
                    .serialize(AugmentCodecs.AUGMENTS_CODEC).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Map<AugmentSlot, CompoundTag>>> AUGMENTS_EXTRA_DATA = ATTACHMENTS.register(
            "augments_extra_data", () -> AttachmentType.<Map<AugmentSlot, CompoundTag>>builder(Collections::emptyMap)
                    .serialize(AugmentCodecs.AUGMENTS_EXTRA_DATA_CODEC).copyOnDeath().build()
    );
    public static final Supplier<AttachmentType<Integer>> AUGMENT_DATA_CHANGED = ATTACHMENTS.register(
            "augment_data_changed", () -> AttachmentType.builder(() -> -1).build()
    );
}
