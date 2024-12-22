package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryParentModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry.*;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

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
        BookEntryModel laserManipulationEntry = new LaserManipulationEntry(this)
                .generate(new Vec2(0, -2));
        add(laserManipulationEntry);
        BookEntryModel mixerEntry = new MixerEntry(this)
                .generate(new Vec2(0, 0));
        add(mixerEntry);
        BookEntryModel chargerEntry = new ChargerEntry(this)
                .generate(new Vec2(0, 2));
        add(chargerEntry);
        BookEntryModel chemistryIntroductionEntry = new ChemistryIntroductionEntry(this)
                .generate(new Vec2(2, 0));
        add(chemistryIntroductionEntry.withParent(mixerEntry));
        BookEntryModel toolsEntry = new ToolsEntry(this)
                .generate(new Vec2(4, 0));
        add(toolsEntry.withParent(chemistryIntroductionEntry).withParent(chargerEntry));
        BookEntryModel crystalShardsEntry = new CrystalShardsEntry(this)
                .generate(new Vec2(6, 0));
        add(crystalShardsEntry.withParent(toolsEntry));
        BookEntryModel drainEntry = new DrainEntry(this)
                .generate(new Vec2(4, -2));
        add(drainEntry.withParent(mixerEntry));
        BookEntryModel batteryEntry = new BatteryEntry(this)
                .generate(new Vec2(8, -2));
        add(batteryEntry.withParent(crystalShardsEntry));
    }

    @Override
    protected String categoryName() {
        return "Laser Chemistry";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(NTItems.ELECTROLYTE_ALGAE_SERUM_VIAL);
    }

    @Override
    public String categoryId() {
        return "laser_chemistry";
    }

    @Override
    protected BookEntryParentModel parent(BookEntryModel parentEntry) {
        return BookEntryParentModel.create(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "getting_started"));
    }
}
