package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class BioReactorEntry extends EntryProvider {
    public BioReactorEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("bio_reactor", () -> BookSpotlightPageModel.create()
                .withTitle(context.pageTitle())
                .withItem(NTBlocks.BIO_REACTOR)
                .withText(context.pageText()));
        pageTitle("Bio Reactor");
        pageText("""
                The Bio Reactor is a Multiblock Machine that
                requires AP to produce resources from bacteria.
                The Bio Reactor can handle up to 3 bacteria
                colonies at once. The power cost scales per
                colony. The amount of time it takes for a colony
                to produce its resource is dependent on the size
                and production rate of the colony. A high size and
                high production rate will lead to faster production
                times compared to a small size and low production rate.
                """);
        this.page("bio_reactor_1", () -> BookMultiblockPageModel.create()
                .withMultiblockId(modLoc("bio_reactor"))
                .withText(context.pageText()));
        this.pageText("""
                All production recipes can be viewed in JEI.
                """);
    }

    @Override
    protected String entryName() {
        return "Bio Reactor";
    }

    @Override
    protected String entryDescription() {
        return "Doesn't produce power";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.BIO_REACTOR);
    }

    @Override
    protected String entryId() {
        return "bio_reactor";
    }
}
