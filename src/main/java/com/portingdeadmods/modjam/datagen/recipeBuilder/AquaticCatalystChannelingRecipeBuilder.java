package com.portingdeadmods.modjam.datagen.recipeBuilder;

import com.portingdeadmods.modjam.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AquaticCatalystChannelingRecipeBuilder implements RecipeBuilder {
    @NotNull
    private Ingredient ingredient;
    private final int powerAmount;

    private AquaticCatalystChannelingRecipeBuilder(Ingredient ingredient, int powerAmount) {
        this.ingredient = ingredient;
        this.powerAmount = powerAmount;
    }

    public static AquaticCatalystChannelingRecipeBuilder newRecipe(Ingredient ingredient, int powerAmount) {
        return new AquaticCatalystChannelingRecipeBuilder(ingredient, powerAmount);
    }

    @Override
    public @NotNull AquaticCatalystChannelingRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NotNull AquaticCatalystChannelingRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        AquaticCatalystChannelingRecipe recipe = new AquaticCatalystChannelingRecipe(this.ingredient, this.powerAmount);
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
