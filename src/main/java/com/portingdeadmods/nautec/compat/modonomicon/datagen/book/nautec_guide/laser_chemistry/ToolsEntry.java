package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_chemistry;

import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.registries.NTItems;

public class ToolsEntry extends BaseNautecEntry {
    public ToolsEntry(CategoryProviderBase parent) {
        super(parent, "tools", "Aquarine Steel Tools", "Innocent looking", BookIconModel.create(NTItems.AQUARINE_AXE));
    }

    @Override
    protected void generatePages() {
        this.page("basic_tools", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Aquarine Steel Tools");
        this.pageText("""
                The Aquarine Steel Tools are quite special. They use Aquatic Power to function and are unbreakable.
                \\
                By infusing them in EAS fluid, they can be upgraded to have special abilities.
                \\
                Each tool has a unique ability that is explained in the tooltips.
                \\
                Shift-right-clicking with the tool in hand will activate the ability.
                """);
        this.page("tools_recipe", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Sword Recipe")
                .withRecipeId1("nautec:aquarine_sword")
                .withTitle2("Pickaxe Recipe")
                .withRecipeId2("nautec:aquarine_pickaxe"));

        this.page("tools_recipe_2", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Axe Recipe")
                .withRecipeId1("nautec:aquarine_axe")
                .withTitle2("Shovel Recipe")
                .withRecipeId2("nautec:aquarine_shovel"));

        this.page("tools_recipe_3", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Hoe Recipe")
                .withRecipeId1("nautec:aquarine_hoe"));

    }
}
