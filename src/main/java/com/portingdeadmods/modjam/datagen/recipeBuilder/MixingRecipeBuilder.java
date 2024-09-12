package com.portingdeadmods.modjam.datagen.recipeBuilder;

import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.content.recipes.MixingRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MixingRecipeBuilder implements MJRecipeBuilder {
    private List<IngredientWithCount> ingredients;
    private FluidStack fluidIngredient;
    private ItemStack result;
    private FluidStack resultFluid;
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

    public MixingRecipeBuilder fluidIngredient(FluidStack fluidIngredient) {
        this.fluidIngredient = fluidIngredient;
        return this;
    }

    public MixingRecipeBuilder fluidResult(FluidStack resultFluid) {
        this.resultFluid = resultFluid;
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
        MixingRecipe recipe = new MixingRecipe(ingredients, fluidIngredient, result, resultFluid, duration);
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
