package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;

public class ChargerEntry extends EntryProvider {
    public ChargerEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("charger", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Charger");
        this.pageText("""
                The charger is a block that can be used to charge items with Aquatic Power.
                \\
                To use it, shoot it with a laser beam from an energy source.\s
                \\
                The charger will then charge the item in the slot above it.
               \s""");
        this.page("charger_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Charger Recipe")
                .withRecipeId1("nautec:charger"));
    }

    @Override
    protected String entryName() {
        return "Charger";
    }

    @Override
    protected String entryDescription() {
        return "Laser Charging ????";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.CHARGER);
    }

    @Override
    protected String entryId() {
        return "charger";
    }
}
