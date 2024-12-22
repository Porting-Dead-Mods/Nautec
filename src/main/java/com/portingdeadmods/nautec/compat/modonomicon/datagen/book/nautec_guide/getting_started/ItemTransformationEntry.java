package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.resources.ResourceLocation;

public class ItemTransformationEntry extends EntryProvider {
    public ItemTransformationEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page("item_transformation", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Item Transformation");
        this.pageText("""
                Item transformation works by shooting lasers at items to transform them into other items.
                \\
                \\
                Some recipes may require a certain level of purity so be sure to check before crafting.
                """);
        this.page("transformation_recipe", () -> BookImagePageModel.create()
                .withTitle("Recipe Exemples")
                .withImages(ResourceLocation.fromNamespaceAndPath(Modonomicon.MOD_ID, "textures/gui/book/recipe/transformation_recipes.png")));

    }


    @Override
    protected String entryName() {
        return "Item Transformation";
    }

    @Override
    protected String entryDescription() {
        return "It's a magic mod!";
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(NTItems.AQUARINE_STEEL_INGOT.get());
    }

    @Override
    protected String entryId() {
        return "item_transformation";
    }
}
