package com.portingdeadmods.nautec.compat.modonomicon.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.GettingStartedCategory;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.LaserChemistryCategory;

public class NautecGuide extends SingleBookSubProvider {
    public NautecGuide(ModonomiconLanguageProvider defaultLang) {
        super("nautec_guide", Nautec.MODID, defaultLang);
    }

    @Override
    protected void registerDefaultMacros() {
    }

    @Override
    protected void generateCategories() {
        add(new GettingStartedCategory(this).generate());
        add(new LaserChemistryCategory(this).generate());
    }

    @Override
    protected String bookName() {
        return "Nautec Guide";
    }

    @Override
    protected String bookTooltip() {
        return "Nautec Guide Tooltip";
    }
}
