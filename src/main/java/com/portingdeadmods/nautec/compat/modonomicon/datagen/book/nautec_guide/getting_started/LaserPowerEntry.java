package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.mojang.datafixers.util.Pair;

public class LaserPowerEntry extends EntryProvider {
    public LaserPowerEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {

    }

    @Override
    protected String entryName() {
        return "";
    }

    @Override
    protected String entryDescription() {
        return "";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return null;
    }

    @Override
    protected BookIconModel entryIcon() {
        return null;
    }

    @Override
    protected String entryId() {
        return "";
    }
}
