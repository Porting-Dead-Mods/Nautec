package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class MonocleEntry extends EntryProvider {
    public MonocleEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("monocle", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Prism Monocle");
        this.pageText("""
                        The Prism Monocle is a useful tool that allows you to display informations about the block you are looking at.
                        \\
                        \\
                        It is particularly useful to know how pure is a laser beam.
                        \\
                        \\
                        If some informations are missing, we recommend using the Jade mod as it has a more extensive support for Nautec.
                        """);
        this.page("monocle_recipe", () -> BookCraftingRecipePageModel.create().withTitle1("Recipe").withRecipeId1("nautec:prism_monocle"));
    }

    @Override
    protected String entryName() {
        return "Prismarine Monocle";
    }

    @Override
    protected String entryDescription() {
        return "The Sixth Eye of the Sea";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.PRISM_MONOCLE);
    }

    @Override
    protected String entryId() {
        return "monocle";
    }
}
