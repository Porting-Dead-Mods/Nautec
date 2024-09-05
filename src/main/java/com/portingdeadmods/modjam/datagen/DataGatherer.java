package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ModJam.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGatherer {
    private static final String PATH_PREFIX = "textures/block";
    private static final String PATH_SUFFIX = ".png";

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), new ItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new BlockModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new RecipesProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new net.minecraft.data.loot.LootTableProvider(output, Collections.emptySet(), List.of(
                new net.minecraft.data.loot.LootTableProvider.SubProviderEntry(LootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
        BlockTagProvider blockTagProvider = new BlockTagProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeClient(), blockTagProvider);
        generator.addProvider(event.includeClient(), new ItemTagProvider(output, lookupProvider, blockTagProvider.contentsGetter()));
    }
}