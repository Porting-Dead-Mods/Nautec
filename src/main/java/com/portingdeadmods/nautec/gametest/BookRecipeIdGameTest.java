package com.portingdeadmods.nautec.gametest;

import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.book.entries.BookContentEntry;
import com.klikli_dev.modonomicon.book.entries.BookEntry;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.portingdeadmods.nautec.Nautec;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.List;

@GameTestHolder(Nautec.MODID)
@PrefixGameTestTemplate(false)
public class BookRecipeIdGameTest {

    @GameTest(template = "test_empty")
    public static void allBookRecipeIdsResolve(GameTestHelper helper) {
        RecipeManager recipes = helper.getLevel().getRecipeManager();
        List<String> errors = new ArrayList<>();

        for (Book book : BookDataManager.get().getBooks().values()) {
            for (BookEntry entry : book.getEntries().values()) {
                if (!(entry instanceof BookContentEntry contentEntry)) continue;
                int pageIndex = 0;
                for (BookPage page : contentEntry.getPages()) {
                    if (page instanceof BookRecipePage<?> recipePage) {
                        check(recipes, recipePage.getRecipeId1(), book.getId(), entry.getId(), pageIndex, 1, errors);
                        check(recipes, recipePage.getRecipeId2(), book.getId(), entry.getId(), pageIndex, 2, errors);
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

    private static void check(RecipeManager recipes, ResourceLocation recipeId, ResourceLocation bookId, ResourceLocation entryId, int pageIndex, int slot, List<String> errors) {
        if (recipeId == null) return;
        if (recipes.byKey(recipeId).isEmpty()) {
            errors.add(bookId + " > " + entryId + " page " + pageIndex + " slot " + slot + " -> " + recipeId);
        }
    }
}
