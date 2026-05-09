package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class BacterialAnalyzerEntry extends BaseNautecEntry {
    public BacterialAnalyzerEntry(CategoryProviderBase parent) {
        super(parent, "bacterial_analyzer", "Bacterial Analyzer", "Ohhhh looky looky", BookIconModel.create(NTBlocks.BACTERIAL_ANALYZER));
    }

    @Override
    protected void generatePages() {
        page("bacteria_analyzer", () -> BookSpotlightPageModel.create()
                .withTitle(context.pageTitle())
                .withItem(NTBlocks.BACTERIAL_ANALYZER)
                .withText(context.pageText()));
        pageTitle("Bacterial Analyzer");
        pageText("""
                The Bacterial Analyzer is a machine that requires AP
                to analyze Bacteria Colonies. Analyzing colonies allows
                you to obtain information about them like their size
                and stats. Analysis works by supplying the analyzer with
                a Petri Dish containing unanalyzed Bacteria. The machine
                will then output the petridish now containing analyzed Bacteria.
                """);
        this.page("bacterial_analyzer_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Bacterial Analyzer Recipe")
                .withRecipeId1("nautec:bacterial_analyzer"));
    }
}
