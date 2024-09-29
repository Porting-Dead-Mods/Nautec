package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.content.augments.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTAugments {
    public static final DeferredRegister<AugmentType<?>> AUGMENTS = DeferredRegister.create(NTRegistries.AUGMENT_TYPE, Nautec.MODID);

    public static final Supplier<AugmentType<ThrowRandomPotionAugments>> THROW_POTION_AUGMENT = AUGMENTS.register("throw_random_potion",
            () -> AugmentType.of(ThrowRandomPotionAugments::new));
    public static final Supplier<AugmentType<DrownedLungAugment>> DROWNED_LUNG = AUGMENTS.register("drowned_lung",
            () -> AugmentType.of(DrownedLungAugment::new, NTAugmentSlots.LUNG.get()));
    public static final Supplier<AugmentType<DolphinFinAugment>> DOLPHIN_FIN = AUGMENTS.register("dolphin_fin",
            () -> AugmentType.of(DolphinFinAugment::new, NTAugmentSlots.BODY.get()));
    public static final Supplier<AugmentType<ThrowBouncingTridentAugment>> THROWN_BOUNCING_TRIDENT_AUGMENT = AUGMENTS.register("throw_bouncing_trident",
            () -> AugmentType.of(ThrowBouncingTridentAugment::new));
    public static final Supplier<AugmentType<GuardianEyeAugment>> GUARDIAN_EYE = AUGMENTS.register("guardian_eye",
            () -> AugmentType.of(GuardianEyeAugment::new, NTAugmentSlots.EYES.get()));
    public static final Supplier<AugmentType<LeapAugment>> LEAP_AUGMENT = AUGMENTS.register("leap",
            () -> AugmentType.of(LeapAugment::new));
    public static final Supplier<AugmentType<PreventFallDamageAugment>> PREVENT_FALL_DAMAGE_AUGMENT = AUGMENTS.register("prevent_fall_damage",
            () -> AugmentType.of(PreventFallDamageAugment::new));
    public static final Supplier<AugmentType<StepUpAugment>> STEP_UP_AUGMENT = AUGMENTS.register("step_up",
            () -> AugmentType.of(StepUpAugment::new));
    public static final Supplier<AugmentType<UnderwaterMiningSpeed>> UNDERWATER_MINING_SPEED_AUGMENT = AUGMENTS.register("underwater_mining_speed",
            () -> AugmentType.of(UnderwaterMiningSpeed::new));
    public static final Supplier<AugmentType<BonusHeartsAugment>> BONUS_HEART_AUGMENT = AUGMENTS.register("bonus_hearts",
            () -> AugmentType.of(BonusHeartsAugment::new));
    public static final Supplier<AugmentType<CreativeFlightAugment>> CREATIVE_FLIGHT_AUGMENT = AUGMENTS.register("creative_flight",
            () -> AugmentType.of(CreativeFlightAugment::new));
    public static final Supplier<AugmentType<MagnetAugment>> MAGNET_AUGMENT = AUGMENTS.register("magnet",
            () -> AugmentType.of(MagnetAugment::new));
    public static final Supplier<AugmentType<EldritchHeartAugment>> ELDRITCH_HEART = AUGMENTS.register("eldritch_heart",
            () -> AugmentType.of(EldritchHeartAugment::new, NTAugmentSlots.HEART.get()));
    public static final Supplier<AugmentType<WalkingSpeedAugment>> WALKING_SPEED_AUGMENT = AUGMENTS.register("walking_speed",
            () -> AugmentType.of(WalkingSpeedAugment::new));
    public static final Supplier<AugmentType<ThrowSpreadingTrident>> SPREADING_TRIDENT_AUGMENT = AUGMENTS.register("spreading_trident",
            () -> AugmentType.of(ThrowSpreadingTrident::new));
    public static final Supplier<AugmentType<EnderMagnetAugment>> ENDER_MAGNET_AUGMENT = AUGMENTS.register("ender_magnet",
            () -> AugmentType.of(EnderMagnetAugment::new));
}
