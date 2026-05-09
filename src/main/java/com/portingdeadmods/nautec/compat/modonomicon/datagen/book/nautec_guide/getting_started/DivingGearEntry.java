package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class DivingGearEntry extends BaseNautecEntry {
    public DivingGearEntry(CategoryProviderBase parent) {
        super(parent, "diving_gear", "Diving Suit and Oxygen", "Explore the depths of the ocean", BookIconModel.create(NTItems.DIVING_HELMET.get()));
    }

    @Override
    protected void generatePages() {
        this.page("diving", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Diving Gear");
        this.pageText("""
                The Diving Suit is a special suit that allows you to explore the depths of the ocean.
                \\
                Crafted from a special Polymer material, the Diving Suit is must-have for any underwater explorer.
                \\
                The Helmet will clear your vision underwater while the chestplate will provide you with 10min of oxygen.
                """);
        this.page("oxygen_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle2("Filling the tanks")
                .withRecipeId2("nautec:diving_chestplate_oxygen")
                .withTitle1("Brown Polymer")
                .withRecipeId1("nautec:brown_polymer"));
    }
}
