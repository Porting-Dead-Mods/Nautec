package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTBlocks;

public class MixerEntry extends EntryProvider {
    public MixerEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
            this.page("mixer", () -> BookTextPageModel.create()
                    .withTitle(this.context().pageTitle())
                    .withText(this.context().pageText()));
            this.pageText("""
                    The Mixer is a powerful machine that can mix and combine items in a variety of ways.
                    \\
                    Materials can be inserted manually, note however that you can only input one ingredient
                    per horizontal direction.
                    \\
                    It requires laser power to operate. To display informations about the ongoing process, you can use the Monocle.
                    """);
        page("mixer_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Mixer Recipe")
                .withRecipeId1("nautec:mixer")
                .withText(this.context().pageText()));
        pageText("""
                Its main purpose is to mix ingredients into chemicals like EAS or Etching Acid, used to infuse tools to unlock their full potential.
                """);
    }

    @Override
    protected String entryName() {
        return "The Mixer";
    }

    @Override
    protected String entryDescription() {
        return "Mixing my way through the ocean";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTBlocks.MIXER);
    }

    @Override
    protected String entryId() {
        return "mixer";
    }
}
