package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class MachinePartEntry extends BaseNautecEntry {
    public MachinePartEntry(CategoryProviderBase parent) {
        super(parent, "machine_part", "Ancient Machine Parts", "All over the place", BookIconModel.create(NTItems.ANCIENT_VALVE));
    }

    @Override
    protected void generatePages() {
        this.page("machine_part", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Machine Parts");
        this.pageText("""
                On your journey trough the ocean
                you will find lots of seemingly
                dirty or damaged items that are
                in fact required for most machinery
                and even tools.
                \\
                \\
                Note however, that most of these items
                need to be cleaned or repaired before
                being used.
                """);

        this.page("machine_part_examples", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.BURNT_COIL));
    }
}
