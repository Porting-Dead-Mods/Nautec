package com.portingdeadmods.modjam.datagen.recipe_builder;

import com.portingdeadmods.modjam.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemTransformationRecipeBuilder implements RecipeBuilder {
    @NotNull private final List<IngredientWithCount> ingredients;
    @NotNull private final ItemStack result;

    private ItemTransformationRecipeBuilder(ItemStack result) {
        this.ingredients = new ArrayList<>();
        this.result = result;
    }

    public static ItemTransformationRecipeBuilder newRecipe(ItemStack result) {
        return new ItemTransformationRecipeBuilder(result);
    }

    @Override
    public @NotNull ItemTransformationRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    public ItemTransformationRecipeBuilder ingredients(IngredientWithCount... ingredients) {
        this.ingredients.addAll(Arrays.stream(ingredients).toList());
        return this;
    }
    public ItemTransformationRecipeBuilder ingredients(ItemStack... items) {
        this.ingredients.addAll(Arrays.stream(items).map(IngredientWithCount::fromItemStack).toList());
        return this;
    }

    public ItemTransformationRecipeBuilder ingredients(ItemLike... items) {
        this.ingredients.addAll(Arrays.stream(items).map(IngredientWithCount::fromItemLike).toList());
        return this;
    }

    @SafeVarargs
    public final ItemTransformationRecipeBuilder ingredients(TagKey<Item>... items) {
        this.ingredients.addAll(Arrays.stream(items).map(IngredientWithCount::fromItemTag).toList());
        return this;
    }

    @Override
    public @NotNull ItemTransformationRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        ItemTransformationRecipe recipe = new ItemTransformationRecipe(this.ingredients, this.result);
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
