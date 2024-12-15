package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.bacteria.Bacteria;
import com.portingdeadmods.nautec.bacteria.BacteriaImpl;
import com.portingdeadmods.nautec.registries.NTBacteria;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BacteriaProvider extends DatapackBuiltinEntriesProvider {
    public BacteriaProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, null, Set.of(Nautec.MODID));
    }
}
