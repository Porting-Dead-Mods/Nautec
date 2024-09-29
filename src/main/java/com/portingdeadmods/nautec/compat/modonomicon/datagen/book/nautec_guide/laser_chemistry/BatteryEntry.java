package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class BatteryEntry extends EntryProvider {
    public BatteryEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("prismatic_battery", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Prismatic Battery");
        this.pageText("""
                The Prismatic Battery is a powerful energy storage device that can store up to 10,000 AP
                \\
                It is mainly used to charge tools and armor in your inventory.
                \\
                Shift-right-clicking with the battery in your hand will enable it.
                \\
                When enabled, the battery will charge any item in your inventory that can be charged.
                \\
                (Note: It has to be in it's curios slot to function)
                """);
        this.page("batter_recipe",() -> BookCraftingRecipePageModel.create()
                .withTitle1("Prismatic Battery Recipe")
                .withRecipeId1("nautec:prismatic_battery"));
    }

    @Override
    protected String entryName() {
        return "Prismatic Battery";
    }

    @Override
    protected String entryDescription() {
        return "Charger²";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.PRISMATIC_BATTERY);
    }

    @Override
    protected String entryId() {
        return "prismatic_battery";
    }
}
