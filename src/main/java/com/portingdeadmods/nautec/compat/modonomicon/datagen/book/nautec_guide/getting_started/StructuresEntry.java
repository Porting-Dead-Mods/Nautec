package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class StructuresEntry extends BaseNautecEntry {
    public StructuresEntry(CategoryProviderBase parent) {
        super(parent, "structures", "Structures", "Structures that you will come across on your journey through the oceans", BookIconModel.create(NTBlocks.PRISMARINE_CRYSTAL));
    }

    @Override
    protected void generatePages() {
        this.page("structures", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));

        this.pageTitle("Deep Sea Structures");
        this.pageText("""
                The first step to unlocking Aquatic Power is to locate an ocean and to search for a small and unlikely structure.
                \\
                The structure is mainly built out of dark prismarine with a mysterious device in the middle of the "arch"
                However, this structure should not be your main point of interest as below it, in a small radius, there often
                is a geode like cave, containing a mighty crystal.
                """);

        this.page("geode", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));

        this.pageTitle("Deep Sea Structures");
        this.pageText("""
                Around the crystal there is also a variety of loot. The crystal itself is surrounded with scaffolding which might be useful
                later on...
                \\
                \\
                Edit: Do NOT break the crystal (yet) as it seems to shatter completely, without leaving anything behind.
                """);
    }
}
