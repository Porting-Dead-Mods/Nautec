package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.content.augments.AugmentSlots;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJAugmentSlots {
    public static final DeferredRegister<AugmentSlot> AUGMENT_SLOTS = DeferredRegister.create(MJRegistries.AUGMENT_SLOT, ModJam.MODID);

    public static final Supplier<AugmentSlot> HEAD = AUGMENT_SLOTS.register("head", () -> AugmentSlots.HEAD);
    public static final Supplier<AugmentSlot> BODY = AUGMENT_SLOTS.register("body", () -> AugmentSlots.BODY);
    public static final Supplier<AugmentSlot> ARMS = AUGMENT_SLOTS.register("arms", () -> AugmentSlots.ARMS);
    public static final Supplier<AugmentSlot> LEGS = AUGMENT_SLOTS.register("legs", () -> AugmentSlots.LEGS);
    public static final Supplier<AugmentSlot> HEART = AUGMENT_SLOTS.register("heart", () -> AugmentSlots.HEART);
}
