package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.augments.DisallowBreakingAugment;
import com.portingdeadmods.modjam.content.augments.GiveDiamondAugment;
import com.portingdeadmods.modjam.content.augments.StaticAugment;
import com.portingdeadmods.modjam.content.augments.ThrowSnowballAugment;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJAugments {
    public static final DeferredRegister<StaticAugment> AUGMENTS = DeferredRegister.create(MJRegistries.AUGMENT, ModJam.MODID);

    public static final Supplier<DisallowBreakingAugment> DISALLOW_BREAKING = AUGMENTS.register("disallow_breaking", DisallowBreakingAugment::new);
    public static final Supplier<GiveDiamondAugment> GIVE_DIAMOND = AUGMENTS.register("give_diamond", GiveDiamondAugment::new);
    public static final Supplier<ThrowSnowballAugment> THROW_SNOWBALL = AUGMENTS.register("throw_snowball", ThrowSnowballAugment::new);
}
