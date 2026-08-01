package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MixingRecipeBuilder implements NTRecipeBuilder {
    private List<IngredientWithCount> ingredients;
    @Nullable
    private FluidStackTemplate fluidIngredient;
    @Nullable
    private final ItemStackTemplate result;
    @Nullable
    private FluidStackTemplate resultFluid;
    private int duration;

    private MixingRecipeBuilder(@Nullable ItemStackTemplate result) {
        this.result = result;
        this.duration = 120;
    }

    public static MixingRecipeBuilder newRecipe(ItemStackTemplate result) {
        return new MixingRecipeBuilder(result);
    }

    public static MixingRecipeBuilder newRecipe() {
        return new MixingRecipeBuilder(null);
    }

    public MixingRecipeBuilder ingredients(IngredientWithCount... ingredients) {
        this.ingredients = List.of(ingredients);
        return this;
    }

    public MixingRecipeBuilder fluidIngredient(@Nullable FluidStackTemplate fluidIngredient) {
        this.fluidIngredient = fluidIngredient;
        return this;
    }

    public MixingRecipeBuilder fluidResult(@Nullable FluidStackTemplate resultFluid) {
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
        return result != null ? result.item().value() : Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> key) {
        MixingRecipe recipe = new MixingRecipe(
                ingredients,
                Optional.ofNullable(fluidIngredient),
                Optional.ofNullable(result),
                Optional.ofNullable(resultFluid),
                duration
        );
        recipeOutput.accept(key, recipe, null);
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
