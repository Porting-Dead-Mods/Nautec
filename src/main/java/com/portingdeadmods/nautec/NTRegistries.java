package com.portingdeadmods.nautec;

import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStatsSerializer;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class NTRegistries {
    public static final ResourceKey<Registry<AugmentType<?>>> AUGMENT_TYPE_KEY =
            ResourceKey.createRegistryKey(Nautec.rl("augment_type"));
    public static final ResourceKey<Registry<AugmentSlot>> AUGMENT_SLOT_KEY =
            ResourceKey.createRegistryKey(Nautec.rl("augment_slot"));
    private static final ResourceKey<Registry<Multiblock>> MULTIBLOCK_KEY =
            ResourceKey.createRegistryKey(Nautec.rl("multiblock"));
    public static final ResourceKey<Registry<BacteriaSerializer<?>>> BACTERIA_SERIALIZER_KEY =
            ResourceKey.createRegistryKey(Nautec.rl("bacteria_serializer"));
    public static final ResourceKey<Registry<BacteriaStatsSerializer<?, ?>>> BACTERIA_STATS_SERIALIZER_KEY =
            ResourceKey.createRegistryKey(Nautec.rl("bacteria_stats_serializer"));
    public static final ResourceKey<Registry<Bacteria>> BACTERIA_KEY =
            ResourceKey.createRegistryKey(Nautec.rl("bacteria"));

    public static final Registry<AugmentType<?>> AUGMENT_TYPE = new RegistryBuilder<>(AUGMENT_TYPE_KEY).sync(true).create();
    public static final Registry<AugmentSlot> AUGMENT_SLOT = new RegistryBuilder<>(AUGMENT_SLOT_KEY).sync(true).create();
    public static final Registry<Multiblock> MULTIBLOCK = new RegistryBuilder<>(MULTIBLOCK_KEY).create();
    public static final Registry<BacteriaSerializer<?>> BACTERIA_SERIALIZER = new RegistryBuilder<>(BACTERIA_SERIALIZER_KEY).create();
    public static final Registry<BacteriaStatsSerializer<?, ?>> BACTERIA_STATS_SERIALIZER = new RegistryBuilder<>(BACTERIA_STATS_SERIALIZER_KEY).sync(true).create();
}
