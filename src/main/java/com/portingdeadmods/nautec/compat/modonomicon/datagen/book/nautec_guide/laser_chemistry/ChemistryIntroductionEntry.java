package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class ChemistryIntroductionEntry extends EntryProvider {
    public ChemistryIntroductionEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("chemistry", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Introduction to chemistry");
        this.pageText("""
                Chemistry is a powerful concept that while mysterious is
                also quite interesting. An observation i have made is that chemistry
                is heavily focused around the ocean and mainly uses plants
                or magical crystals as ingredients for substances.
                \\
                \\
                Even though all of this might sound foreign, you have already
                come across a chemical substance, Etching Acid.
                While not typical in its structure, it is still considered a chemical fluid
                due to its transforming abilities.
                """);
        this.page("eas", () -> BookSpotlightPageModel.create()
                        .withTitle(this.context().pageTitle())
                        .withItem(NTItems.EAS_BUCKET.get())
                        .withText(this.context().pageText()));
        this.pageTitle("Electrolyte Algae Serum");
        this.pageText("""
                Electrolyte Algae Serum (EAS) is one of the first chemicals
                you will come across. It is known for
                its transforming abilities that can empower
                items and entities.
                """);

        this.page("eas", () -> BookSpotlightPageModel.create()
                        .withTitle(this.context().pageTitle())
                        .withItem(NTItems.SALT_WATER_BUCKET.get())
                        .withText(this.context().pageText()));
        this.pageTitle("Electrolyte Algae Serum");
        this.pageText("""
                You might have already discovered that it uses another liquid: Salt Water
                \\
                Salt water can be obtained by filling a bucket of water in an ocean biome which
                will turn it into salt water. In the future there are even more powerful ways
                to gather it, so stay tuned!
                """);
    }

    @Override
    protected String entryName() {
        return "Introduction to Chemistry";
    }

    @Override
    protected String entryDescription() {
        return "It's not gregtech, I swear";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.EAS_BUCKET.get());
    }

    @Override
    protected String entryId() {
        return "chemistry_introduction";
    }
}
