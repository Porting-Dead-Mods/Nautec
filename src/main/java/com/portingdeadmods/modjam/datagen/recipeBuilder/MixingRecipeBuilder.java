package com.portingdeadmods.modjam.datagen.recipeBuilder;

import com.portingdeadmods.modjam.content.recipes.MixingRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MixingRecipeBuilder implements RecipeBuilder {
    private List<IngredientWithCount> ingredients;
    private FluidStack fluidIngredient;
    private final ItemStack result;
    private FluidStack fluidResult;
    private int duration;

    public MixingRecipeBuilder(ItemStack result) {
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

    public MixingRecipeBuilder fluidIngredient(FluidStack fluidIngredient) {
        this.fluidIngredient = fluidIngredient;
        return this;
    }

    public MixingRecipeBuilder fluidResult(FluidStack fluidResult) {
        this.fluidResult = fluidResult;
        return this;
    }

    public MixingRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        if (ingredients.isEmpty()) {
            throw new IllegalStateException("Cannot generate recipe without ingredients, affected recipe: " + getClass().getName() + ", result: " + result);
        }

        recipeOutput.accept(id, new MixingRecipe(ingredients, fluidIngredient, result, fluidResult, duration), null);
    }
}
