package com.portingdeadmods.modjam.datagen.recipeBuilder;

import com.portingdeadmods.modjam.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemEtchingRecipeBuilder implements MJRecipeBuilder {
    @NotNull
    private IngredientWithCount ingredient;
    @NotNull private final ItemStack result;

    private ItemEtchingRecipeBuilder(ItemStack result) {
        this.ingredient = IngredientWithCount.EMPTY;
        this.result = result;
    }

    public static ItemEtchingRecipeBuilder newRecipe(ItemStack result) {
        return new ItemEtchingRecipeBuilder(result);
    }

    @Override
    public @NotNull ItemEtchingRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
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

    public final ItemEtchingRecipeBuilder ingredient(TagKey<Item> item) {
        this.ingredient = IngredientWithCount.fromItemTag(item);
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
        ItemEtchingRecipe recipe = new ItemEtchingRecipe(this.ingredient, this.result);
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
