package com.portingdeadmods.nautec.compat.modonomicon.datagen;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider;
import com.klikli_dev.modonomicon.api.datagen.research.ResearchCache;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.NautecGuide;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.NautecGuideMultiblockProvider;
import com.portingdeadmods.nautec.datagen.EnUsProvider;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class ModonomiconDatagen {
    public static void register(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        LanguageProviderCache enUsCache = new LanguageProviderCache("en_us");
        ResearchCache researchCache = new ResearchCache();
        event.addProvider(NeoBookProvider.of(event, enUsCache, researchCache, new NautecGuide()));
        event.addProvider(new EnUsProvider(generator.getPackOutput(), enUsCache));
        event.addProvider(new NautecGuideMultiblockProvider(generator.getPackOutput()));
    }
}
