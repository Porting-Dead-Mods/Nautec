package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;

public class LaserChemistryCategory extends CategoryProvider {
    public LaserChemistryCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[0];
    }

    @Override
    protected void generateEntries() {

    }

    @Override
    protected String categoryName() {
        return "";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return null;
    }

    @Override
    public String categoryId() {
        return "";
    }
}
