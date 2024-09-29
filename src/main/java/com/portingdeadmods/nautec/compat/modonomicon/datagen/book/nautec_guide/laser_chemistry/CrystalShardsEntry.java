package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class CrystalShardsEntry extends EntryProvider {
    public CrystalShardsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("prismarine_crystal_shards", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Prismarine Crystal Shard");
        this.pageText("""
                Crystal shards are end of chapter :3
                """);
    }

    @Override
    protected String entryName() {
        return "Prismarine Crystal Shards";
    }

    @Override
    protected String entryDescription() {
        return "Not an amethyst rextexture!";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.PRISMARINE_CRYSTAL_SHARD.get());
    }

    @Override
    protected String entryId() {
        return "crystal_shards";
    }
}
