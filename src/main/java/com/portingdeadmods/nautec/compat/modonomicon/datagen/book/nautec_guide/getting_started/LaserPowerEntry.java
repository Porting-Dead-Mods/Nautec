package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class LaserPowerEntry extends EntryProvider {
    public LaserPowerEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("An Introduction to Laser Power");
        this.pageText("""
                Lasers are a mysterious technology. Nobody really knows
                exactly how to create them. However using some prismarine
                both dark and light, one is able to create an Aquatic Catalyst.
                \\
                While this block itself does not possesses the ability to channel
                energy into a laser.
                """);
        this.page("aquatic_catalyst_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Aquatic Catalyst")
                .withRecipeId1("nautec:aquatic_catalyst")
                .withText("""
                        It can be accomplished by supplying the catalyst with fuel, like for
                        example Kelp and activating it with a Heart Of the Sea.
                        \\
                        After right-clicking the item on the catalyst, a laser beam will shoot
                        out of the opposite side as long as it has a target.
                        """));
        this.page("purity", () -> BookTextPageModel.create()
                .withTitle("Purity")
                .withText("""
                        By shooting a laser beam into a crystal, one can increase the lasers purity.
                        The lasers that are created by this shot (top and bottom) now have a purity of 3.0
                        this means they can be used for Item Transformation!
                        """));
    }

    @Override
    protected String entryName() {
        return "Laser Power";
    }

    @Override
    protected String entryDescription() {
        return "Whoooooooosh";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.AQUATIC_CATALYST);
    }

    @Override
    protected String entryId() {
        return "laser_power";
    }
}
