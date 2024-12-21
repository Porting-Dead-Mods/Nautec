package com.portingdeadmods.nautec;

import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStatsSerializer;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class NTRegistries {
    private static final ResourceKey<Registry<AugmentType<?>>> AUGMENT_TYPE_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "augment_type"));
    public static final ResourceKey<Registry<AugmentSlot>> AUGMENT_SLOT_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "augment_slot"));
    private static final ResourceKey<Registry<Multiblock>> MULTIBLOCK_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "multiblock"));
    public static final ResourceKey<Registry<BacteriaSerializer<?>>> BACTERIA_SERIALIZER_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "bacteria_serializer"));
    public static final ResourceKey<Registry<BacteriaStatsSerializer<?>>> BACTERIA_STATS_SERIALIZER_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "bacteria_stats_serializer"));
    public static final ResourceKey<Registry<Bacteria>> BACTERIA_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "bacteria"));

    public static final Registry<AugmentType<?>> AUGMENT_TYPE = new RegistryBuilder<>(AUGMENT_TYPE_KEY).create();
    public static final Registry<AugmentSlot> AUGMENT_SLOT = new RegistryBuilder<>(AUGMENT_SLOT_KEY).create();
    public static final Registry<Multiblock> MULTIBLOCK = new RegistryBuilder<>(MULTIBLOCK_KEY).create();
    public static final Registry<BacteriaSerializer<?>> BACTERIA_SERIALIZER = new RegistryBuilder<>(BACTERIA_SERIALIZER_KEY).create();
    public static final Registry<BacteriaStatsSerializer<?>> BACTERIA_STATS_SERIALIZER = new RegistryBuilder<>(BACTERIA_STATS_SERIALIZER_KEY).create();
}
