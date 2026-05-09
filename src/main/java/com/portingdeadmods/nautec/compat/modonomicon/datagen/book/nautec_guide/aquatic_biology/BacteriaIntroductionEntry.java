package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class BacteriaIntroductionEntry extends BaseNautecEntry {
    public BacteriaIntroductionEntry(CategoryProviderBase parent) {
        super(parent, "bacteria_introduction", "Bacteria Introduction", "Exponential Growth", BookIconModel.create(NTItems.PRISMARINE_LENS));
    }

    @Override
    protected void generatePages() {
        page("bacteria_introduction", () -> BookTextPageModel.create()
                .withTitle(context.pageTitle())
                .withText(context.pageText()));
        pageTitle("Bacteria Introduction");
        pageText("""
                Bacteria is a way of generating
                an infinite amount of resources.
                The system works similar to Forestry's
                bees. Bacteria is most commonly handled
                using the Petri Dish, in which one can
                store a single Bacteria Colony.
                """);
        page("bacteria_introduction2", () -> BookTextPageModel.create()
                .withText(context.pageText()));
        pageText("""
                Bacteria Colonies have properties like
                the size that impacts the Bacteria's
                production speed and mutation capabilities.
                Bacteria also has a variety of stats which
                will be explained in greater detail later on.
                """);
    }
}
