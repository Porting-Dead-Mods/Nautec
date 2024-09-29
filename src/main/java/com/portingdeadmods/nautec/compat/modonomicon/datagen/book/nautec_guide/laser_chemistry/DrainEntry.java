package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;

public class DrainEntry extends EntryProvider {
    public DrainEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("drain", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Deep Sea Drain");
        this.pageText("""
                The Deep Sea Drain is a mutliblock dedicated
                to draining huge amounts of salt water.
                \\
                In order to do so, it must be constructed in an
                ocean biome. After forming the multiblock, shift-right-click
                on the center valve block to open it.
                Also make sure to right-click one of the middle wall blocks
                with a wrench to open a laser port and supply it with power.
                """);

        this.page("drain_multi", () -> BookMultiblockPageModel.create()
                .withMultiblockId(modLoc("drain"))
                .withVisualizeButton(true)
                .withText(this.context().pageText()));
        this.pageText("""
                To form the deep sea drain, you
                needs to right-click the center block with
                an Aquarine Steel Wrench.
                \\
                Be Careful: The drain will suck in any entity! Even items
                """);
    }

    @Override
    protected String entryName() {
        return "Deep Sea Drain";
    }

    @Override
    protected String entryDescription() {
        return "Sucks in everything";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.DRAIN);
    }

    @Override
    protected String entryId() {
        return "drain";
    }
}
