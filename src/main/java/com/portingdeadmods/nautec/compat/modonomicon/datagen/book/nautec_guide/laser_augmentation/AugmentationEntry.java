package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class AugmentationEntry extends EntryProvider {
    public AugmentationEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("augmentation", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Player Augmentation");
        this.pageText("""
                To start the augmentation process, make sure each
                Augmentation Station Extension has an augment item
                and a Robot Arm as well as being supplied with enough
                Power.
                \\
                Next, step onto the middle of the Augmentation Station
                where a screen should open, in which you can configure
                the augment and start its application.
                """);
        this.page("robot_arm", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.CLAW_ROBOT_ARM)
                .withText(this.context().pageText()));
        this.pageTitle("Robot arm");
        this.pageText("""
                Used in the augmentation station, which when paired with a Mob Augment allows the player to augment the player with the supplied augment.
                Check the Mob Augments page for more info on what you can augment with.
                """);
    }

    @Override
    protected String entryName() {
        return "Player Augmentation";
    }

    @Override
    protected String entryDescription() {
        return "Infinite Possibilities";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.CLAW_ROBOT_ARM);
    }

    @Override
    protected String entryId() {
        return "augmentation";
    }
}
