package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class IntroductionEntry extends EntryProvider {
    public IntroductionEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Introduction");
        this.pageText("""
                Greetings Traveller,
                \\
                it seems like you have obtained
                my book. I hope to explain my discoveries
                about the vast underwater world and how to leverage them in it.
                \\
                \\
                To make this easier to follow along I have decided to structure
                it in a way that allows for easy understanding and
                replication of my steps.
                """);
    }

    @Override
    protected String entryName() {
        return "Introduction";
    }

    @Override
    protected String entryDescription() {
        return "Intro desc";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.PRISMARINE_SAND.get());
    }

    @Override
    protected String entryId() {
        return "introduction";
    }
}
