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
        this.page("laser_relay", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Prismarine Laser Relay")
                .withRecipeId1("nautec:prismarine_laser_relay")
                .withText(this.context().pageText()));
        this.pageText("""
                The Prismarine Laser Relay is a block that allows you to extend the range of your lasers.
                Make sure to point the arrow in the right direction!
                \\
                It is crafted using 6 Polish Prismarine blocks.
                """);
        this.page("laser_junction0", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Laser Junction")
                .withRecipeId1("nautec:laser_junction")
                .withText(this.context.pageText()));
        this.pageText("""
                The Laser Junction is a block that allows you to split a laser beam into multiple directions
                or bundle multiple Beams into a single one.
                """);
        this.page("laser_junction1", () -> BookTextPageModel.create()
                .withText(this.context.pageText()));
        this.pageText("""
                Right-click or Shift-right-click to toggle input output on the blocks side.
                While this block is quite expensive, it is also a useful and very powerful block to have.
                """);
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
