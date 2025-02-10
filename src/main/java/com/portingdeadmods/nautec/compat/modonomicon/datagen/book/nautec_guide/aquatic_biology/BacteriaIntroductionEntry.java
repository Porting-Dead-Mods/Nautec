package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class BacteriaIntroductionEntry extends EntryProvider {
    public BacteriaIntroductionEntry(CategoryProviderBase parent) {
        super(parent);
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

    @Override
    protected String entryName() {
        return "Bacteria Introduction";
    }

    @Override
    protected String entryDescription() {
        return "Exponential Growth";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.PRISMARINE_LENS);
    }

    @Override
    protected String entryId() {
        return "bacteria_introduction";
    }
}
