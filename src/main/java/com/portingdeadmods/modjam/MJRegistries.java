package com.portingdeadmods.modjam;

import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class MJRegistries {
    private static final ResourceKey<Registry<AugmentType<?>>> AUGMENT_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "augment_type"));
    public static final ResourceKey<Registry<AugmentSlot>> AUGMENT_SLOT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "augment_slot"));
    private static final ResourceKey<Registry<Multiblock>> MULTIBLOCK_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "multiblock"));

    public static final Registry<AugmentType<?>> AUGMENT_TYPE = new RegistryBuilder<>(AUGMENT_TYPE_KEY).create();
    public static final Registry<AugmentSlot> AUGMENT_SLOT = new RegistryBuilder<>(AUGMENT_SLOT_KEY).create();
    public static final Registry<Multiblock> MULTIBLOCK = new RegistryBuilder<>(MULTIBLOCK_KEY).create();
}
