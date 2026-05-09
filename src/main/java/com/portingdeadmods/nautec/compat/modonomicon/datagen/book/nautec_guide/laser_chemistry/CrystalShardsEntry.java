package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class CrystalShardsEntry extends BaseNautecEntry {
    public CrystalShardsEntry(CategoryProviderBase parent) {
        super(parent, "crystal_shards", "Prismarine Crystal Shards", "Not an amethyst rextexture!", BookIconModel.create(NTItems.PRISMARINE_CRYSTAL_SHARD.get()));
    }

    @Override
    protected void generatePages() {
        this.page("prismarine_crystal_shards", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.PRISMARINE_CRYSTAL_SHARD)
                .withText(this.context().pageText()));
        this.pageTitle("Prismarine Crystal Shard");
        this.pageText("""
                Prismarine Crystal Shards are a pristine
                material required for some of the most
                advanced machinery.
                """);
        this.page("shard_and_crystal", () -> BookTextPageModel.create()
                .withText(this.context().pageText()));
        this.pageText("""
                In order to obtain them you have to
                use the Aquarine Steel Pickaxe with
                its ability enabled and mine a Prismarine crystal.
                \\
                Make sure that it has more than 100 power as that is
                the amount it needs to preserve the drops.
                """);
    }
}
