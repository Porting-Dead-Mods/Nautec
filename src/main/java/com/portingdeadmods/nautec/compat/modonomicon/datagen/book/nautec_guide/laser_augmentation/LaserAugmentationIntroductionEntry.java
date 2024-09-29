package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class LaserAugmentationIntroductionEntry extends EntryProvider {
    public LaserAugmentationIntroductionEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("laser_augmentation_intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Introduction to Laser Augmentation");
        this.pageText("""
                Now that you have made such huge progress in your studies,
                you are able to explore the most dangerous parts of science.
                Body Augmentation. Since this is such an
                advanced topic I have dedicated this entire chapter to it.
                \\
                \\
                Read it carefully and with patience.
                """);
    }

    @Override
    protected String entryName() {
        return "Introduction to Laser Augmentation";
    }

    @Override
    protected String entryDescription() {
        return "Beginning of the end";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.ELDRITCH_HEART);
    }

    @Override
    protected String entryId() {
        return "laser_augmentation_introduction";
    }
}
