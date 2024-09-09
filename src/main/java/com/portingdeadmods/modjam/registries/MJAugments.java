package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.augments.*;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJAugments {
    public static final DeferredRegister<StaticAugment> AUGMENTS = DeferredRegister.create(MJRegistries.AUGMENT, ModJam.MODID);


    public static final Supplier<EmptyAugment> EMPTY_AUGMENT = AUGMENTS.register("empty_augment", EmptyAugment::new);
    public static final Supplier<DisallowBreakingAugment> DISALLOW_BREAKING = AUGMENTS.register("disallow_breaking", DisallowBreakingAugment::new);
    public static final Supplier<GiveDiamondAugment> GIVE_DIAMOND = AUGMENTS.register("give_diamond", GiveDiamondAugment::new);
    public static final Supplier<ThrowSnowballAugment> THROW_SNOWBALL = AUGMENTS.register("throw_snowball", ThrowSnowballAugment::new);
    public static final Supplier<ThrowRandomPotionAugments> THROW_POTION_AUGMENT = AUGMENTS.register("throw_random_potion", ThrowRandomPotionAugments::new);
    public static final Supplier<PreventPlayerLoseAirAugment> PREVENT_PLAYER_LOSE_AIR_AUGMENT = AUGMENTS.register("prevent_player_lose_air_supply", PreventPlayerLoseAirAugment::new);
}
