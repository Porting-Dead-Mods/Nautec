package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.content.augments.slots.SimpleAugmentSlot;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTAugmentSlots {
    public static final DeferredRegister<AugmentSlot> AUGMENT_SLOTS = DeferredRegister.create(NTRegistries.AUGMENT_SLOT, Nautec.MODID);

    public static final Supplier<AugmentSlot> HEAD = AUGMENT_SLOTS.register("head", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> EYES = AUGMENT_SLOTS.register("eyes", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> BODY = AUGMENT_SLOTS.register("body", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> LUNG = AUGMENT_SLOTS.register("lung", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> LEFT_ARM = AUGMENT_SLOTS.register("left_arm", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> RIGHT_ARM = AUGMENT_SLOTS.register("right_arm", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> LEFT_LEG = AUGMENT_SLOTS.register("left_leg", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> RIGHT_LEG = AUGMENT_SLOTS.register("right_leg", SimpleAugmentSlot::new);
    public static final Supplier<AugmentSlot> HEART = AUGMENT_SLOTS.register("heart", SimpleAugmentSlot::new);
}
