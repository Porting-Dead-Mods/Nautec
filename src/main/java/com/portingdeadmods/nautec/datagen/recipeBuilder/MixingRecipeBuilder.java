package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MixingRecipeBuilder implements NTRecipeBuilder {
    private List<IngredientWithCount> ingredients;
    @Nullable
    private FluidStack fluidIngredient;  // Can now be null
    private ItemStack result;
    @Nullable
    private FluidStack resultFluid;  // Can now be null
    private int duration;

    private MixingRecipeBuilder(ItemStack result) {
        this.result = result;
        this.duration = 120;
    }

    public static MixingRecipeBuilder newRecipe(ItemStack result) {
        return new MixingRecipeBuilder(result);
    }

    public MixingRecipeBuilder ingredients(IngredientWithCount... ingredients) {
        this.ingredients = List.of(ingredients);
        return this;
    }

    public MixingRecipeBuilder fluidIngredient(@Nullable FluidStack fluidIngredient) {
        this.fluidIngredient = fluidIngredient;  // Optional fluid ingredient
        return this;
    }

    public MixingRecipeBuilder fluidResult(@Nullable FluidStack resultFluid) {
        this.resultFluid = resultFluid;  // Optional fluid result
        return this;
    }

    public MixingRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

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
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        // Create the recipe, handling null fluidIngredient and resultFluid
        MixingRecipe recipe = new MixingRecipe(
                ingredients,
                fluidIngredient != null ? fluidIngredient : FluidStack.EMPTY,  // Use FluidStack.EMPTY if null
                result,
                resultFluid != null ? resultFluid : FluidStack.EMPTY,  // Use FluidStack.EMPTY if null
                duration
        );
        recipeOutput.accept(resourceLocation, recipe, null);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return ingredients.stream().map(IngredientWithCount::ingredient).toList();
    }

    @Override
    public String getName() {
        return MixingRecipe.NAME;
    }
}
