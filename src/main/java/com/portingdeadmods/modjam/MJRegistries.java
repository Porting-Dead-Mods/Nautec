package com.portingdeadmods.modjam;

import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.augments.StaticAugment;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class MJRegistries {
    private static final ResourceKey<Registry<StaticAugment>> AUGMENT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "augment"));
    private static final ResourceKey<Registry<Multiblock>> MULTIBLOCK_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "multiblock"));
    public static final Registry<StaticAugment> AUGMENT = new RegistryBuilder<>(AUGMENT_KEY).create();
    public static final Registry<Multiblock> MULTIBLOCK = new RegistryBuilder<>(MULTIBLOCK_KEY).create();
}
