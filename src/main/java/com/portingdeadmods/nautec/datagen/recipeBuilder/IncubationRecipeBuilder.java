package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.recipes.BacteriaIncubationRecipe;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public record IncubationRecipeBuilder(ResourceKey<Bacteria> bacteria, Ingredient nutrient, IntRange growth, float consumeChance) implements NTRecipeBuilder {
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
        // Create the recipe, handling null fluidIngredient and resultFluid
        BacteriaIncubationRecipe recipe = new BacteriaIncubationRecipe(
                bacteria, nutrient, growth, consumeChance
        );
        recipeOutput.accept(resourceLocation, recipe, null);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return Collections.singletonList(nutrient);
    }

    @Override
    public String getName() {
        return BacteriaMutationRecipe.NAME;
    }
}
