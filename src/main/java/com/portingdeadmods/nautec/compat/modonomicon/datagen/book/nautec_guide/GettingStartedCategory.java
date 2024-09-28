package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started.IntroductionEntry;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;

public class GettingStartedCategory extends CategoryProvider {
    public GettingStartedCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[0];
    }

    @Override
    protected void generateEntries() {
        add(new IntroductionEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Getting Started";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(NTBlocks.PRISMARINE_CRYSTAL.get());
    }

    @Override
    public String categoryId() {
        return "getting_started";
    }
}
