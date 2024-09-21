package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.content.augments.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJAugments {
    public static final DeferredRegister<AugmentType<?>> AUGMENTS = DeferredRegister.create(MJRegistries.AUGMENT_TYPE, ModJam.MODID);

    public static final Supplier<AugmentType<DisallowBreakingAugment>> DISALLOW_BREAKING = AUGMENTS.register("disallow_breaking",
            () -> AugmentType.of(DisallowBreakingAugment::new));
    public static final Supplier<AugmentType<GiveDiamondAugment>> GIVE_DIAMOND = AUGMENTS.register("give_diamond",
            () -> AugmentType.of(GiveDiamondAugment::new));
    public static final Supplier<AugmentType<ThrowSnowballAugment>> THROW_SNOWBALL = AUGMENTS.register("throw_snowball",
            () -> AugmentType.of(ThrowSnowballAugment::new));
    public static final Supplier<AugmentType<ThrowRandomPotionAugments>> THROW_POTION_AUGMENT = AUGMENTS.register("throw_random_potion",
            () -> AugmentType.of(ThrowRandomPotionAugments::new));
    public static final Supplier<AugmentType<PreventPlayerLoseAirAugment>> PREVENT_PLAYER_LOSE_AIR_AUGMENT = AUGMENTS.register("prevent_player_lose_air_supply",
            () -> AugmentType.of(PreventPlayerLoseAirAugment::new));
    public static final Supplier<AugmentType<DolphinFinAugment>> UNDERWATER_MOVEMENT_SPEED_AUGMENT = AUGMENTS.register("underwater_movement_speed",
            () -> AugmentType.of(DolphinFinAugment::new));
    public static final Supplier<AugmentType<ThrowBouncingTridentAugment>> THROWN_BOUNCING_TRIDENT_AUGMENT = AUGMENTS.register("throw_bouncing_trident",
            () -> AugmentType.of(ThrowBouncingTridentAugment::new));
    public static final Supplier<AugmentType<GuardianEyeAugment>> GUARDIAN_EYE_AUGMENT = AUGMENTS.register("guardian_eye",
            () -> AugmentType.of(GuardianEyeAugment::new));
    public static final Supplier<AugmentType<LeapAugment>> LEAP_AUGMENT = AUGMENTS.register("leap",
            () -> AugmentType.of(LeapAugment::new));
    public static final Supplier<AugmentType<PreventFallDamageAugment>> PREVENT_FALL_DAMAGE_AUGMENT = AUGMENTS.register("prevent_fall_damage",
            () -> AugmentType.of(PreventFallDamageAugment::new));
}
