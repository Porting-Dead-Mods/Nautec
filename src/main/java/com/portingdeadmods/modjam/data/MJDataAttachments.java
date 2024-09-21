package com.portingdeadmods.modjam.data;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.utils.AugmentCodecs;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public final class MJDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ModJam.MODID);

    public static final Supplier<AttachmentType<Map<AugmentSlot, Augment>>> AUGMENTS = ATTACHMENTS.register(
            "augments", () -> AttachmentType.<Map<AugmentSlot, Augment>>builder(Collections::emptyMap)
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