package com.portingdeadmods.modjam;

import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class MJRegistries {
    private static final ResourceKey<Registry<Multiblock>> MULTIBLOCK_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "multiblock"));
    public static final Registry<Multiblock> MULTIBLOCK = new RegistryBuilder<>(MULTIBLOCK_KEY).create();
}
