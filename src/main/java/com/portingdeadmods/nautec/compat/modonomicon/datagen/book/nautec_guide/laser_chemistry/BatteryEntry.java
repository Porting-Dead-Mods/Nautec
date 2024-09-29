package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class BatteryEntry extends EntryProvider {
    public BatteryEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("prismatic_battery", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Prismatic Battery");
        this.pageText("""
                Curios slot, charges inventory
                """);
    }

    @Override
    protected String entryName() {
        return "Prismatic Battery";
    }

    @Override
    protected String entryDescription() {
        return "Charger²";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.PRISMATIC_BATTERY);
    }

    @Override
    protected String entryId() {
        return "prismatic_battery";
    }
}
