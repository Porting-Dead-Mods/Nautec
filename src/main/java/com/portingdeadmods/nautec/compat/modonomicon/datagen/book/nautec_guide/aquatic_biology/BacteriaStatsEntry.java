package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class BacteriaStatsEntry extends BaseNautecEntry {
    public BacteriaStatsEntry(CategoryProviderBase parent) {
        super(parent, "bacteria_stats", "Bacteria Stats", "Statistics raaaaaaawr", BookIconModel.create(NTItems.PETRI_DISH));
    }

    @Override
    protected void generatePages() {
        page("bacteria_stats", () -> BookTextPageModel.create()
                .withTitle(context.pageTitle()))
                .withText(context.pageText());
        pageTitle("Bacteria Stats");
        pageText("""
                After you have analyzed your bacteria, you will notice
                that pressing shift while hovering over the item will
                display information about the bacteria's stats. Stats
                can vary between bacteria, but most of the time there
                are the following stats:
                \
                - Growth Rate, The speed of growth in the incubator
                \
                - Mutation Resistance, The Resistance against Mutation in the Mutator
                \
                - Production Rate, The Production Rate of Resources in the Bio Reactor
                \
                - Lifespan, The Time it takes for a colony to shrink when producing resources
                """);
    }
}
