package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemTransformationRecipeBuilder implements NTRecipeBuilder {
    @NotNull
    private final ItemStackTemplate result;
    private IngredientWithCount ingredient;
    private float purity;
    private int duration;

    private ItemTransformationRecipeBuilder(ItemStackTemplate result) {
        this.result = result;
    }

    public static ItemTransformationRecipeBuilder newRecipe(ItemStackTemplate result) {
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

    public ItemTransformationRecipeBuilder ingredient(TagKey<Item> item) {
        this.ingredient = IngredientWithCount.fromItemTag(item);
        return this;
    }

    public ItemTransformationRecipeBuilder purity(float purity) {
        this.purity = purity;
        return this;
    }

    public ItemTransformationRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public @NotNull ItemTransformationRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result.item().value();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> key) {
        ItemTransformationRecipe recipe = new ItemTransformationRecipe(this.ingredient, this.result, this.duration, this.purity);
        recipeOutput.accept(key, recipe, null);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return List.of(this.ingredient.ingredient());
    }

    @Override
    public String getName() {
        return ItemTransformationRecipe.NAME;
    }
}
