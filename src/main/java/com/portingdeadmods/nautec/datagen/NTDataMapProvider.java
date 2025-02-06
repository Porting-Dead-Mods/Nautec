package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.data.NTDataMaps;
import com.portingdeadmods.nautec.data.maps.BacteriaObtainValue;
import com.portingdeadmods.nautec.registries.NTBacterias;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class NTDataMapProvider extends DataMapProvider {
    protected NTDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        obtainBacteria(Blocks.STONE, NTBacterias.CYANOBACTERIA, BiomeTags.IS_OCEAN, 0.4f);
    }

    private void obtainBacteria(Block block, ResourceKey<Bacteria> bacteria, TagKey<Biome> biome, float chance) {
        builder(NTDataMaps.BACTERIA_OBTAINING)
                .add(block.builtInRegistryHolder(), new BacteriaObtainValue(bacteria, biome, chance), false);
    }
}
