package com.portingdeadmods.nautec.compat.modonomicon.datagen;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.NautecGuide;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.NautecGuideMultiblockProvider;
import com.portingdeadmods.nautec.datagen.EnUsProvider;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class ModonomiconDatagen {
    public static void register(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        LanguageProviderCache enUsCache = new LanguageProviderCache("en_us");
        generator.addProvider(event.includeServer(), NeoBookProvider.of(event, new NautecGuide(enUsCache)));
        generator.addProvider(event.includeClient(), new EnUsProvider(generator.getPackOutput(), enUsCache));
        generator.addProvider(event.includeServer(), new NautecGuideMultiblockProvider(generator.getPackOutput()));
    }
}
