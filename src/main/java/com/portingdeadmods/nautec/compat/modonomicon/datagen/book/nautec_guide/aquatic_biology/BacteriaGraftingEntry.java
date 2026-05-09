package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class BacteriaGraftingEntry extends BaseNautecEntry {
    public BacteriaGraftingEntry(CategoryProviderBase parent) {
        super(parent, "bacteria_grafting", "Bacteria Grafting", "Crafting the Grafting", BookIconModel.create(NTItems.GRAFTING_TOOL));
    }

    @Override
    protected void generatePages() {
        page("bacteria_grafting", () -> BookSpotlightPageModel.create()
                .withTitle(context.pageTitle()))
                .withItem(NTItems.GRAFTING_TOOL)
                .withText(context.pageText());
        pageTitle("Bacteria Grafting");
        pageText("""
                Grafting is the initial way of obtaining primitive
                Bacteria. It is done by holding a Grafting Tool in your main
                hand and a Petri Dish in your off hand. Then right
                click on the specified block in the correct biome
                and obtain the bacteria. Note that there is a chance
                for getting bacteria when right clicking. Bacteria
                that can be grafted can be viewed in JEI.
                """);

        page("bacteria_overview", () -> BookTextPageModel.create()
                .withTitle(context.pageTitle()))
                .withText(context.pageText());
        pageTitle("Bacteria Overview");
        pageText("""
                The obtained bacteria does not provide much
                information about its stats or the colony's size.
                For this you need to analyze the bacteria.
                """);
    }
}
