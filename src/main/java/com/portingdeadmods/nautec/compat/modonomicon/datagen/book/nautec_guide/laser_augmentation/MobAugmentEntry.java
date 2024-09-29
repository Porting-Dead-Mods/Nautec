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
                The Guardian Eye gives you the ability to shoot a laser at the entity you are looking at, within a 15 block radius.
                Note however, that in order to do this you need to hold down a keybind.
                \\
                \\
                The item can be obtained as a rare drop from Guardians.
                """);
        this.page("dolphin_fin", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.DOLPHIN_FIN)
                .withText(this.context().pageText()));
        this.pageTitle("Dolphin Fin");
        this.pageText("""
                The Dolphin Fin affects you with the Dolphins Grace effect while swimming.
                \\
                This allows you to explore the water more efficiently.
                \\
                \\
                This item can be obtained as a rare drop from Dolphins.
                """);
        this.page("drowned_lung", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.DROWNED_LUNGS)
                .withText(this.context().pageText()));
        this.pageTitle("Drowned Lungs");
        this.pageText("""
                The Drowned Lungs is one of the most powerful augments.
                \\
                They allow you to breathe both underwater and on the surface
                \\
                \\
                This item can be obtained as a rare drop from Drowned.
                """);
        this.page("eldritch_heart", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.ELDRITCH_HEART)
                .withText(this.context().pageText()));
        this.pageTitle("Eldritch_heart");
        this.pageText("""
                The Eldritch Heart will increase your Regeneration under water.
                \\
                While this might not sound too overwhelming on its own, it is
                worth noting, that the eldritch heart is also required for the
                Augmentation Station.
                \\
                \\
                This item can be obtained from Elder Guardians.
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
