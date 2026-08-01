package com.portingdeadmods.nautec.compat.modonomicon.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

public abstract class BaseNautecEntry extends EntryProvider {
    private final String id;
    private final String name;
    private final String description;
    private final BookIconModel icon;

    protected BaseNautecEntry(CategoryProviderBase parent, String id, String name, String description, BookIconModel icon) {
        super(parent);
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    @Override
    protected String entryId() {
        return id;
    }

    @Override
    protected String entryName() {
        return name;
    }

    @Override
    protected String entryDescription() {
        return description;
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return icon;
    }
}
