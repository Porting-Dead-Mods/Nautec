package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
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

public class ItemEtchingRecipeBuilder implements MJRecipeBuilder {
    @NotNull
    private final ItemStack result;
    @NotNull
    private IngredientWithCount ingredient;
    private int duration;

    private ItemEtchingRecipeBuilder(@NotNull ItemStack result) {
        this.ingredient = IngredientWithCount.EMPTY;
        this.result = result;
    }

    public static ItemEtchingRecipeBuilder newRecipe(ItemStack result) {
        return new ItemEtchingRecipeBuilder(result);
    }

    @Override
    public @NotNull ItemEtchingRecipeBuilder unlockedBy(@NotNull String s, @NotNull Criterion<?> criterion) {
        return this;
    }

    public ItemEtchingRecipeBuilder ingredient(IngredientWithCount ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public ItemEtchingRecipeBuilder ingredient(ItemStack ingredient) {
        this.ingredient = IngredientWithCount.fromItemStack(ingredient);
        return this;
    }

    public ItemEtchingRecipeBuilder ingredient(ItemLike ingredient) {
        this.ingredient = IngredientWithCount.fromItemLike(ingredient);
        return this;
    }

    public ItemEtchingRecipeBuilder ingredient(TagKey<Item> item) {
        this.ingredient = IngredientWithCount.fromItemTag(item);
        return this;
    }

    public ItemEtchingRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public @NotNull ItemEtchingRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        ItemEtchingRecipe recipe = new ItemEtchingRecipe(this.ingredient, this.result, this.duration);
        recipeOutput.accept(resourceLocation, recipe, null);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return List.of(this.ingredient.ingredient());
    }

    @Override
    public String getName() {
        return ItemEtchingRecipe.NAME;
    }
}
