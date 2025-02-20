package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.NautecGuide;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology.*;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.world.phys.Vec2;

public class AquaticBiologyCategory extends CategoryProvider {
    public AquaticBiologyCategory(NautecGuide nautecGuide) {
        super(nautecGuide);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[0];
    }

    @Override
    protected void generateEntries() {
        BookEntryModel bacteriaIntroduction = new BacteriaIntroductionEntry(this).generate(new Vec2(0, 0));
        add(bacteriaIntroduction);
        BookEntryModel graftingEntry = new BacteriaGraftingEntry(this).generate(new Vec2(2, 0));
        add(graftingEntry.withParent(bacteriaIntroduction));
        BookEntryModel bacterialAnalyzer = new BacterialAnalyzerEntry(this).generate(new Vec2(4, 0));
        add(bacterialAnalyzer.withParent(graftingEntry));
        BookEntryModel bacteriaStats = new BacteriaStatsEntry(this).generate(new Vec2(4, -2));
        add(bacteriaStats.withParent(bacterialAnalyzer));
        BookEntryModel mutator = new MutatorEntry(this).generate(new Vec2(6, -1));
        add(mutator.withParent(bacterialAnalyzer));
        BookEntryModel incubator = new IncubatorEntry(this).generate(new Vec2(6, 1));
        add(incubator.withParent(bacterialAnalyzer));
        BookEntryModel bioReactor = new BioReactorEntry(this).generate(new Vec2(8, 0));
        add(bioReactor.withParent(incubator).withParent(mutator));
    }

    @Override
    protected String categoryName() {
        return "Aquatic Biology";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(NTItems.PETRI_DISH);
    }

    @Override
    public String categoryId() {
        return "aquatic_biology";
    }
}
