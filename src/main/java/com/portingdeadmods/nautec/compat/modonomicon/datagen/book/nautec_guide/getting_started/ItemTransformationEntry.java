package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.Modonomicon;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.resources.Identifier;

public class ItemTransformationEntry extends BaseNautecEntry {
    public ItemTransformationEntry(CategoryProviderBase parent) {
        super(parent, "item_transformation", "Item Transformation", "It's a magic mod!", BookIconModel.create(NTItems.AQUARINE_STEEL_INGOT.get()));
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
                .withImages(Identifier.fromNamespaceAndPath(Modonomicon.MOD_ID, "textures/gui/book/recipe/transformation_recipes.png")));

    }
}
