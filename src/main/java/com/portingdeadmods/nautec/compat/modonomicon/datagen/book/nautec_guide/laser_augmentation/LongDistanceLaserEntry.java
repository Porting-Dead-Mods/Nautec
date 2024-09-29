package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class LongDistanceLaserEntry extends EntryProvider {
    public LongDistanceLaserEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("long_distance_laser", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Long Distance Laser");
        this.pageText("""
                The Long Distance Laser allows you to shoot
                laser up to 64 blocks rather than the typical
                16 blocks. Use this block wisely, as it might
                cause more lag than regular laser devices.
                """);

        this.page("ldl_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Long Distance Laser Recipe")
                .withRecipeId1("nautec:long_distance_laser"));
    }

    @Override
    protected String entryName() {
        return "Long Distance Laser";
    }

    @Override
    protected String entryDescription() {
        return "Who needs pipelines when you can have this";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.LONG_DISTANCE_LASER);
    }

    @Override
    protected String entryId() {
        return "long_distance_laser";
    }
}
