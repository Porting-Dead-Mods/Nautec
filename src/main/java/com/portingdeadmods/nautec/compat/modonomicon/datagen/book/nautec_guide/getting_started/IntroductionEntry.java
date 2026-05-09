package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class IntroductionEntry extends BaseNautecEntry {
    public IntroductionEntry(CategoryProviderBase parent) {
        super(parent, "introduction", "Introduction", "Introducing... NAUTEC", BookIconModel.create(NTBlocks.PRISMARINE_SAND.get()));
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Introduction");
        this.pageText("""
                Welcome to NauTec, the underwater
                tech-magic mod. It offers a quite unique
                progression with options for automation,
                player augmentation and complex contraptions.
                \\
                Starting NauTec is quite straight-forward.
                You can start by exploring beaches, gather
                prismarine and build an aquatic catalyst.
                The next pages will teach you how to do this
                in greater detail.
                """);
    }
}
