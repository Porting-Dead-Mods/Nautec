package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;

public class AugmentationStationEntry extends EntryProvider {
    public AugmentationStationEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("augmentation_station", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Augmentation Station");
        this.pageText("""
                Build the multi, put in robot arms, supply with power
                """);
    }

    @Override
    protected String entryName() {
        return "Augmentation Station";
    }

    @Override
    protected String entryDescription() {
        return "Its getting serious";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.AUGMENTATION_STATION);
    }

    @Override
    protected String entryId() {
        return "augmentation_station";
    }
}
