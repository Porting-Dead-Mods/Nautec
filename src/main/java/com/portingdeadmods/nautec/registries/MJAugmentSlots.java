package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.MJRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.content.augments.AugmentSlots;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJAugmentSlots {
    public static final DeferredRegister<AugmentSlot> AUGMENT_SLOTS = DeferredRegister.create(MJRegistries.AUGMENT_SLOT, Nautec.MODID);

    public static final Supplier<AugmentSlot> HEAD = AUGMENT_SLOTS.register("head", () -> AugmentSlots.HEAD);
    public static final Supplier<AugmentSlot> EYES = AUGMENT_SLOTS.register("eyes", () -> AugmentSlots.HEAD);
    public static final Supplier<AugmentSlot> BODY = AUGMENT_SLOTS.register("body", () -> AugmentSlots.BODY);
    public static final Supplier<AugmentSlot> LUNG = AUGMENT_SLOTS.register("lung", () -> AugmentSlots.LUNG);
    public static final Supplier<AugmentSlot> LEFT_ARM = AUGMENT_SLOTS.register("left_arm", () -> AugmentSlots.LEFT_ARM);
    public static final Supplier<AugmentSlot> RIGHT_ARM = AUGMENT_SLOTS.register("right_arm", () -> AugmentSlots.RIGHT_ARM);
    public static final Supplier<AugmentSlot> LEFT_LEG = AUGMENT_SLOTS.register("left_leg", () -> AugmentSlots.LEFT_LEG);
    public static final Supplier<AugmentSlot> RIGHT_LEG = AUGMENT_SLOTS.register("right_leg", () -> AugmentSlots.RIGHT_LEG);
    public static final Supplier<AugmentSlot> HEART = AUGMENT_SLOTS.register("heart", () -> AugmentSlots.HEART);
}
