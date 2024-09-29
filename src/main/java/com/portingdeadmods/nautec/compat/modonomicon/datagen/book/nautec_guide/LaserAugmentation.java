package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class LaserAugmentation extends CategoryProvider {
    public LaserAugmentation(ModonomiconProviderBase parent) {
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
        return "Laser Augmentation";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(NTItems.CLAW_ROBOT_ARM);
    }

    @Override
    public String categoryId() {
        return "laser_augmentation";
    }
}
