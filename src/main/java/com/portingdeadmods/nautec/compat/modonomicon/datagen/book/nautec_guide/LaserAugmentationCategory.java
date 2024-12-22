package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation.*;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.world.phys.Vec2;

public class LaserAugmentationCategory extends CategoryProvider {
    public LaserAugmentationCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[0];
    }

    @Override
    protected void generateEntries() {
        BookEntryModel laserAugmentationEntry = new LaserAugmentationIntroductionEntry(this)
                .generate(new Vec2(0, 0));
        add(laserAugmentationEntry);
        add(new LongDistanceLaserEntry(this).generate(new Vec2(2, -2)).withParent(laserAugmentationEntry));
        add(new ArmorEntry(this).generate(new Vec2(2, 2)).withParent(laserAugmentationEntry));
        BookEntryModel mobAugmentEntry = new MobAugmentEntry(this).generate(new Vec2(2, 0));
        add(mobAugmentEntry.withParent(laserAugmentationEntry));
        BookEntryModel augmentationStationEntry = new AugmentationStationEntry(this).generate(new Vec2(4, 0));
        add(augmentationStationEntry.withParent(mobAugmentEntry));
        BookEntryModel augmentationEntry = new AugmentationEntry(this).generate(new Vec2(6, 0));
        add(augmentationEntry.withParent(augmentationStationEntry));
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
