package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class LaserAugmentationIntroductionEntry extends BaseNautecEntry {
    public LaserAugmentationIntroductionEntry(CategoryProviderBase parent) {
        super(parent, "laser_augmentation_introduction", "Introduction to Laser Augmentation", "Beginning of the end", BookIconModel.create(NTItems.ELDRITCH_HEART));
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
}
