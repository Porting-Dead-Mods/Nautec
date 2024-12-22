package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class DivingGearEntry extends EntryProvider {
    public DivingGearEntry(CategoryProviderBase parent) {
        super(parent);
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
                .withRecipeId2("minecraft:diving_chestplate_oxygen")
                .withTitle1("Brown Polymer")
                .withRecipeId1("nautec:brown_polymer"));
    }

    @Override
    protected String entryName() {
        return "Diving Suit and Oxygen";
    }

    @Override
    protected String entryDescription() {
        return "Explore the depths of the ocean";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.DIVING_HELMET.get());
    }

    @Override
    protected String entryId() {
        return "diving_gear";
    }
}
