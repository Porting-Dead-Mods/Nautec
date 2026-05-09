package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public record MutationRecipeBuilder(ResourceKey<Bacteria> inputBacteria, ResourceKey<Bacteria> resultBacteria, Ingredient catalyst, float chance) implements NTRecipeBuilder {
    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        BacteriaMutationRecipe recipe = new BacteriaMutationRecipe(inputBacteria, resultBacteria, catalyst, chance);
        recipeOutput.accept(resourceLocation, recipe, null);
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        String path = inputBacteria.location().toString().replace(':', '-')
                + "_" + resultBacteria.location().toString().replace(':', '-')
                + NTRecipeBuilder.ingredientPathSuffix(catalyst);
        save(recipeOutput, Nautec.rl(getName() + "/" + path));
    }

    @Override
    public List<Ingredient> getIngredients() {
        return Collections.singletonList(catalyst);
    }

    @Override
    public String getName() {
        return BacteriaMutationRecipe.NAME;
    }
}
