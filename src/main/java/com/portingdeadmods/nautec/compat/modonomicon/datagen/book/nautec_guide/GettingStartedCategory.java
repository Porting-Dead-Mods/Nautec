package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started.*;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.world.phys.Vec2;

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
        BookEntryModel introductionEntry = new IntroductionEntry(this)
                .generate(new Vec2(0, 0));
        add(introductionEntry);
        BookEntryModel structuresEntry = new StructuresEntry(this)
                .generate(new Vec2(0, -2));
        add(structuresEntry.withParent(introductionEntry));
        BookEntryModel cratesEtching = new CratesEtchingEntry(this)
                .generate(new Vec2(-2, -2));
        add(cratesEtching.withParent(structuresEntry));
        BookEntryModel laserPowerEntry = new LaserPowerEntry(this)
                .generate(new Vec2(2, 0));
        add(laserPowerEntry.withParent(introductionEntry));
        BookEntryModel itemTransformationEntry = new ItemTransformationEntry(this)
                .generate(new Vec2(4, 0));
        add(itemTransformationEntry.withParent(laserPowerEntry));
        BookEntryModel monocleEntry = new MonocleEntry(this)
                .generate(new Vec2(2, 2));
        add(monocleEntry.withParent(laserPowerEntry));
        BookEntryModel divingGear = new DivingGearEntry(this)
                .generate(new Vec2(0, 2));
        add(divingGear.withParent(introductionEntry));
        BookEntryModel utilitiesEntry = new UtilitiesEntry(this)
                .generate(new Vec2(4, -2));
        add(utilitiesEntry.withParent(itemTransformationEntry));
        BookEntryModel machinePartEntry = new MachinePartEntry(this)
                .generate(new Vec2(2, -2));
        add(machinePartEntry.withParent(structuresEntry));
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
