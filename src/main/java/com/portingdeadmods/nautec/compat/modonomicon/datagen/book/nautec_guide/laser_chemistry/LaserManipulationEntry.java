package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class LaserManipulationEntry extends EntryProvider {
    public LaserManipulationEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
            this.page("laser_manipulation", () -> BookTextPageModel.create()
                    .withTitle(this.context().pageTitle())
                    .withText(this.context().pageText()));
            this.pageTitle("Laser Manipulation");
            this.pageText("""
                            The Prismarine Laser Relay is a block that allows you to extend the range of your lasers.
                            Make sure to point the arrow to the right direction!
                            \\
                            It is crafted using 6 Polish Prismarine blocks.
                            \\
                            \\
                            The Laser Junction is a block that allows you to split a laser beam into multiple directions.
                            While the recipe is quite expensive, it is a very useful block to have.
                            """);
            this.page("laser_manipulation_recipe", () -> BookCraftingRecipePageModel.create()
                    .withTitle1("Prismarine Laser Relay")
                    .withRecipeId1("nautec:prismarine_laser_relay")
                    .withTitle2("Laser Junction")
                    .withRecipeId2("nautec:laser_junction"));
    }

    @Override
    protected String entryName() {
        return "Laser Manipulation";
    }

    @Override
    protected String entryDescription() {
        return "Playing with Lasers !";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.LASER_JUNCTION);
    }

    @Override
    protected String entryId() {
        return "laser_manipulation";
    }
}
