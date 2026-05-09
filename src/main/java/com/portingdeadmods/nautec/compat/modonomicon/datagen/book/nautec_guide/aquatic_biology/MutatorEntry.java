package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.aquatic_biology;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;

public class MutatorEntry extends BaseNautecEntry {
    public MutatorEntry(CategoryProviderBase parent) {
        super(parent, "mutator", "Mutator", "Mutating and Radiating", BookIconModel.create(NTBlocks.MUTATOR));
    }

    @Override
    protected void generatePages() {
        page("mutator", () -> BookSpotlightPageModel.create()
                .withTitle(context.pageTitle())
                .withItem(NTBlocks.MUTATOR)
                .withText(context.pageText()));
        pageTitle("Mutator");
        pageText("""
                The Mutator is a machine that requires AP
                to mutate Bacteria Colonies. Mutations allow
                you to modify the Colony's Stats or even
                the entire colony to obtain a different one.
                Mutation works by supplying the Mutator with
                a catalyst item and the bacteria that should
                be modified. It will output the bacteria with
                modified stats or even a modified colony.
                """);
        this.page("mutator_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Mutator Recipe")
                .withRecipeId1("nautec:mutator"))
                .withText(context.pageText());
        pageText("""
                The success and effectiveness of the mutation
                depends on the Mutation resistance and size of
                the colony. Larger colonies with a high resistance
                are harder to mutate than smaller ones with a low
                resistance.
                Look at JEI for all recipes.
                """);
    }
}
