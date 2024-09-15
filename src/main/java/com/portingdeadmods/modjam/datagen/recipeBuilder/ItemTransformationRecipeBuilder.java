package com.portingdeadmods.modjam.datagen.recipeBuilder;

import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemTransformationRecipeBuilder implements MJRecipeBuilder {
    @NotNull
    private final ItemStack result;
    @NotNull
    private IngredientWithCount ingredient;
    private float purity;
    private int duration;

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

    public ItemTransformationRecipeBuilder ingredient(TagKey<Item> item) {
        this.ingredient = IngredientWithCount.fromItemTag(item);
        return this;
    }

    public ItemTransformationRecipeBuilder purity(float purity) {
        this.purity = purity;
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
        ItemTransformationRecipe recipe = new ItemTransformationRecipe(this.ingredient, this.result, this.duration, this.purity);
        recipeOutput.accept(resourceLocation, recipe, null);
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
