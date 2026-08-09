package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.portingdeadmods.nautec.registries.NTItems;

public class DeepFaunaAugmentsEntry extends BaseNautecEntry {
    public DeepFaunaAugmentsEntry(CategoryProviderBase parent) {
        super(parent, "deep_fauna_augments", "Deep Fauna Augments", "Take what the deep grew for itself",
                BookIconModel.create(NTItems.ABYSSAL_ORGAN));
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Deep Fauna Augments");
        this.pageText("""
                The creatures of the new oceans solved problems you have not.
                One sees without light, one carries its own light, and one
                lives on a vent floor without cooking.
                \\
                \\
                Kill them and the parts are yours. Each one applies at the
                Augmentation Station like any other organ.
                """);

        this.page("abyssal_eyes", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.ABYSSAL_ORGAN)
                .withText(this.context().pageText()));
        this.pageTitle("Abyssal Eyes");
        this.pageText("""
                Taken from the Abyssal Maw, which hunts the trenches.
                \\
                Fitted to your eye sockets it grants night vision, but only
                once you are deep enough for it to matter. Near the surface
                the organ stays dormant.
                \\
                \\
                It competes with the Guardian Eye for the same sockets.
                """);

        this.page("photophore_skin", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.LUMINOUS_MEMBRANE)
                .withText(this.context().pageText()));
        this.pageTitle("Photophore Skin");
        this.pageText("""
                Taken from the Lantern Jelly of the Bioluminescent Grove.
                \\
                Grafted over your body it lights up every living thing near
                you while you are in the water, through rock and through
                silt. Useful for finding what is already circling you.
                """);

        this.page("vent_carapace", () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withItem(NTItems.CHITIN_PLATE)
                .withText(this.context().pageText()));
        this.pageTitle("Vent Carapace");
        this.pageText("""
                Four plates off the Vent Crawlers that walk the hydrothermal
                floor without noticing the heat.
                \\
                Plated onto your head or body they add armour, anchor you
                against knockback, and put fire out twice as fast.
                """);
    }
}
