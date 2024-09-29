package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class ArmorEntry extends EntryProvider {
    public ArmorEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("aquarine_steel_armor", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Aquarine Steel Armor");
        this.pageText("""
                Powahhh armor. Requires valves & crystals
                """);
    }

    @Override
    protected String entryName() {
        return "Aquarine Steel Armor";
    }

    @Override
    protected String entryDescription() {
        return "Fancy power quantum super ultra mecha armor";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.AQUARINE_CHESTPLATE);
    }

    @Override
    protected String entryId() {
        return "aquarine_steel_armor";
    }
}
