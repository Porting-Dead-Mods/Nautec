package com.portingdeadmods.nautec.gametest;

import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.book.entries.BookContentEntry;
import com.klikli_dev.modonomicon.book.entries.BookEntry;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.data.BookDataManager;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

public class BookRecipeIdGameTest {

    public static void allBookRecipeIdsResolve(GameTestHelper helper) {
        RecipeManager recipes = helper.getLevel().recipeAccess();
        List<String> errors = new ArrayList<>();

        for (Book book : BookDataManager.get().getBooks().values()) {
            for (BookEntry entry : book.getEntries().values()) {
                if (!(entry instanceof BookContentEntry contentEntry)) continue;
                int pageIndex = 0;
                for (BookPage page : contentEntry.getPages()) {
                    if (page instanceof BookRecipePage<?> recipePage) {
                        check(recipes, recipePage.getRecipeKey1(), book.getId(), entry.getId(), pageIndex, 1, errors);
                        check(recipes, recipePage.getRecipeKey2(), book.getId(), entry.getId(), pageIndex, 2, errors);
                    }
                    pageIndex++;
                }
            }
        }

        if (!errors.isEmpty()) {
            helper.fail("Book recipe references that don't resolve to a real recipe:\n  " + String.join("\n  ", errors));
        }
        helper.succeed();
    }

    private static void check(RecipeManager recipes, ResourceKey<Recipe<?>> recipeKey, Identifier bookId, Identifier entryId, int pageIndex, int slot, List<String> errors) {
        if (recipeKey == null) return;
        if (recipes.byKey(recipeKey).isEmpty()) {
            errors.add(bookId + " > " + entryId + " page " + pageIndex + " slot " + slot + " -> " + recipeKey.identifier());
        }
    }
}
