package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class AugmentationStationEntry extends BaseNautecEntry {
    public AugmentationStationEntry(CategoryProviderBase parent) {
        super(parent, "augmentation_station", "Augmentation Station", "Its getting serious", BookIconModel.create(NTBlocks.AUGMENTATION_STATION));
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
}
