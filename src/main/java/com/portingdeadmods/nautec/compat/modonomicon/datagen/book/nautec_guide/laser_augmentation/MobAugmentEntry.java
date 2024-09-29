package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;

public class MobAugmentEntry extends EntryProvider {
    public MobAugmentEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("guardian_eye", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.GUARDIAN_EYE)
                .withText(this.context().pageText()));
        this.pageTitle("Guardian Eye");
        this.pageText("""
                augment purpose, obtaining...
                """);
        this.page("dolphin_fin", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.DOLPHIN_FIN)
                .withText(this.context().pageText()));
        this.pageTitle("Dolphin Fin");
        this.pageText("""
                augment purpose, obtaining...
                """);
        this.page("drowned_lung", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.DROWNED_LUNGS)
                .withText(this.context().pageText()));
        this.pageTitle("Drowned Lungs");
        this.pageText("""
                augment purpose, obtaining...
                """);
        this.page("eldritch_heart", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.ELDRITCH_HEART)
                .withText(this.context().pageText()));
        this.pageTitle("Eldritch_heart");
        this.pageText("""
                augment purpose, obtaining... (regen in water)
                usage in augmentation station
                """);
    }

    @Override
    protected String entryName() {
        return "Mob Augments";
    }

    @Override
    protected String entryDescription() {
        return "Yep, you need to kill ALL of those";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.DOLPHIN_FIN);
    }

    @Override
    protected String entryId() {
        return "mob_augments";
    }
}
