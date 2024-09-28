package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.content.items.NeptunesTridentItem;
import com.portingdeadmods.nautec.registries.NTItems;

public class MonocleEntry extends EntryProvider {
    public MonocleEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("monocle", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Prism Monocle");
        this.pageText("Reveals lots of stuff");
    }

    @Override
    protected String entryName() {
        return "Prismarine Monocle";
    }

    @Override
    protected String entryDescription() {
        return "Six Eyes?";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.PRISM_MONOCLE);
    }

    @Override
    protected String entryId() {
        return "monocle";
    }
}
