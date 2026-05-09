package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class CratesEtchingEntry extends BaseNautecEntry {
    public CratesEtchingEntry(CategoryProviderBase parent) {
        super(parent, "etching", "Crate & Item Etching", "Feels like brand new!", BookIconModel.create(NTBlocks.RUSTY_CRATE));
    }

    @Override
    protected void generatePages() {
        this.page("etching", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Etching");
        this.pageText("""
                Etching is a mechanic that allows you to remove the rust from rusty objects.
                \\
                The process is simple, by dropping a rusty crate or item into a pool of etching acid, the rust will slowly disappear, and a new shiny object will emerge.
                \\
                You probably noticed that the crates are locked, and you can't open them. This is because the lock is messed up and needs a crowbar to be removed.
                """);
        this.page("etching_acid_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Etching Acid Recipe")
                .withRecipeId1("nautec:etching_acid_crafting"));
    }
}
