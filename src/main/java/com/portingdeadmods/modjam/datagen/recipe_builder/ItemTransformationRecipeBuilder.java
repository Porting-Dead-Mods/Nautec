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

public class ItemTransformationRecipeBuilder implements RecipeBuilder {
    @NotNull private IngredientWithCount ingredient;
    @NotNull private final ItemStack result;

    private ItemTransformationRecipeBuilder(ItemStack result) {
        this.ingredient = IngredientWithCount.EMPTY;
        this.result = result;
    }

    public static ItemTransformationRecipeBuilder newRecipe(ItemStack result) {
        return new ItemTransformationRecipeBuilder(result);
    }

    @Override
    public @NotNull ItemTransformationRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    public ItemTransformationRecipeBuilder ingredient(IngredientWithCount ingredient) {
        this.ingredient = ingredient;
        return this;
    }
    public ItemTransformationRecipeBuilder ingredient(ItemStack ingredient) {
        this.ingredient = IngredientWithCount.fromItemStack(ingredient);
        return this;
    }

    public ItemTransformationRecipeBuilder ingredient(ItemLike ingredient) {
        this.ingredient = IngredientWithCount.fromItemLike(ingredient);
        return this;
    }

    public final ItemTransformationRecipeBuilder ingredient(TagKey<Item> item) {
        this.ingredient = IngredientWithCount.fromItemTag(item);
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
        ItemTransformationRecipe recipe = new ItemTransformationRecipe(this.ingredient, this.result);
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
