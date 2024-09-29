package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class ArmorEntry extends EntryProvider {
    public ArmorEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("aquarine_steel_armor", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Aquarine Steel Armor");
        this.pageText("""
                Now that you have unlocked crystal shards,
                you are able to use them to create armor.
                \\
                While the armor might look weak at first,
                it gains actual attributes when supplied with
                power.
                """);

        this.page("asa_recipes0", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Aquarine Steel Helmet")
                .withRecipeId1("natuec:aquarine_steel_helmet")
                .withTitle2("Aquarine Steel Chestplate")
                .withRecipeId2("nautec:aquarine_steel_chestplate"));

        this.page("asa_recipes1", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Aquarine Steel Leggings")
                .withRecipeId1("natuec:aquarine_steel_leggings")
                .withTitle2("Aquarine Steel Boots")
                .withRecipeId2("nautec:aquarine_steel_boots"));
    }

    @Override
    protected String entryName() {
        return "Aquarine Steel Armor";
    }

    @Override
    protected String entryDescription() {
        return "Fancy power quantum super ultra mecha armor";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.AQUARINE_CHESTPLATE);
    }

    @Override
    protected String entryId() {
        return "aquarine_steel_armor";
    }
}
