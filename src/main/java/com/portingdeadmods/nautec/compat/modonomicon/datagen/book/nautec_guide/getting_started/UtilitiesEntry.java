package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class UtilitiesEntry extends EntryProvider {
    public UtilitiesEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("utilities", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Tools and Utilities");
        this.pageText("""
                The Aquarine Steel Wrench, can be used to rotate blocks and change modes on machinery, it can be crafted using 4 Aquarine Steel Ingots.
                \\
                \\
                The crowbar is another useful tool that will allow you to open the many crates you will find in the ocean.
                """);
        this.page("wrench",()-> BookCraftingRecipePageModel.create()
                .withTitle1("Wrench Recipe")
                .withRecipeId1("nautec:aquarine_steel_wrench")
                .withTitle2("Crowbar Recipe")
                .withRecipeId2("nautec:crowbar"));
    }

    @Override
    protected String entryName() {
        return "Tools and Utilities";
    }

    @Override
    protected String entryDescription() {
        return "Various QoL utilities";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.AQUARINE_WRENCH);
    }

    @Override
    protected String entryId() {
        return "utilities";
    }
}
