package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class IncubatorEntry extends EntryProvider {
    public IncubatorEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("incubator", () -> BookSpotlightPageModel.create()
                .withTitle(context.pageTitle())
                .withItem(NTBlocks.MUTATOR)
                .withText(context.pageText()));
        pageTitle("Incubator");
        pageText("""
                The Incubator is a machine that requires AP
                to incubate Bacteria Colonies. Incubation allows
                a colony to grow and increase its size.
                Incubation works by supplying the Incubator with
                a nutrient item and the bacteria that should
                be incubated. Like in real life mutation works
                by doubling the colony's size. Doubling the size
                has a chance to consume the nutrient.
                The amount of time it takes till the doubling takes
                place depends on the colony's growth rate as well
                as the colony's size. A large colony with a low growth
                rate takes longer to be incubated than a small one with
                a high growth rate.
                Look at JEI for all recipes
                """);
        this.page("incubator_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Incubator Recipe")
                .withRecipeId1("nautec:incubator"))
                .withText(context.pageText());
        pageText("""
                The amount of time it takes till the doubling takes
                place depends on the colony's growth rate as well
                as the colony's size. A large colony with a low growth
                rate takes longer to be incubated than a small one with
                a high growth rate.
                Look at JEI for all recipes
                """);
    }

    @Override
    protected String entryName() {
        return "Incubator";
    }

    @Override
    protected String entryDescription() {
        return "Incubating the colony";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.INCUBATOR);
    }

    @Override
    protected String entryId() {
        return "incubator";
    }
}
