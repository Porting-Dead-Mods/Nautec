package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class UtilitiesEntry extends BaseNautecEntry {
    public UtilitiesEntry(CategoryProviderBase parent) {
        super(parent, "utilities", "Tools and Utilities", "Various QoL utilities", BookIconModel.create(NTItems.AQUARINE_WRENCH));
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
                .withRecipeId1("nautec:aquarine_wrench")
                .withTitle2("Crowbar Recipe")
                .withRecipeId2("nautec:crowbar"));
    }
}
