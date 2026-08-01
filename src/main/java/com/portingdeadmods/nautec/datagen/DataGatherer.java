package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.ModonomiconDatagen;
import com.portingdeadmods.nautec.datagen.loot.BlockLootTableProvider;
import com.portingdeadmods.nautec.datagen.loot.ChestLootTableProvider;
import com.portingdeadmods.nautec.datagen.loot.LootModifierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Nautec.MODID)
public class DataGatherer {
    private static final String PATH_PREFIX = "textures/block";
    private static final String PATH_SUFFIX = ".png";

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ItemModelProvider(output));
        generator.addProvider(true, new BlockModelProvider(output));
        generator.addProvider(true, new NTEquipmentAssetProvider(output));
        generator.addProvider(true, new RecipesProvider.Runner(output, lookupProvider));
        generator.addProvider(true, new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(BlockLootTableProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(provider -> new ChestLootTableProvider(), LootContextParamSets.CHEST)
        ), lookupProvider));
        generator.addProvider(true, new BlockTagProvider(output, lookupProvider));
        generator.addProvider(true, new ItemTagProvider(output, lookupProvider));
        generator.addProvider(true, new LootModifierProvider(output, lookupProvider));
        generator.addProvider(true, new DatapackRegistryProvider(output, lookupProvider));
        generator.addProvider(true, new NTDataMapProvider(output, lookupProvider));

        if (ModList.get().isLoaded("modonomicon")) {
            ModonomiconDatagen.register(event);
        }
    }
}
