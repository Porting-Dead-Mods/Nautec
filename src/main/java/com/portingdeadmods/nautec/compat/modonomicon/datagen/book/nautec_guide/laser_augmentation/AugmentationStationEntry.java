package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

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
                The Augmentation Station is the multiblock required
                for applying augments to the player.
                
                When looking at an augmentation recipe (in JEI) you
                might realise that it takes in up to 4 ingredients.
                These ingredients need to be supplied by putting them
                into the 4 augmentation station extensions. Note that
                for each ingredient you also need to add a robot arm.
                """);

        this.page("augmentation_station_multiblock", () -> BookMultiblockPageModel.create()
                .withMultiblockId(modLoc("aumgentation_station"))
                .withVisualizeButton(true)
                .withText(this.context().pageText()));
        this.pageText("""
                Right-click the Augmentation Station block with
                an Aquarine Steel Wrench to form it.
                \\
                Make sure, the augmentation station extension with
                a robot arm is supplied with at least 15 power.
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
