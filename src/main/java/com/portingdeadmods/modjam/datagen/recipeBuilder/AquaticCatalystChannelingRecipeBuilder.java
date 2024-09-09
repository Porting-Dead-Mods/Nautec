package com.portingdeadmods.modjam.datagen.recipeBuilder;

import com.portingdeadmods.modjam.content.recipes.AquaticCatalystChannelingRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AquaticCatalystChannelingRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final int powerAmount;
    private final int duration;

    private AquaticCatalystChannelingRecipeBuilder(Ingredient ingredient, int powerAmount) {
        this.ingredient = ingredient;
        this.powerAmount = powerAmount;
        this.duration = 100;
    }

    private AquaticCatalystChannelingRecipeBuilder(Ingredient ingredient, int powerAmount, int duration) {
        this.ingredient = ingredient;
        this.powerAmount = powerAmount;
        this.duration = duration;
    }

    public static AquaticCatalystChannelingRecipeBuilder newRecipe(Ingredient ingredient, int powerAmount) {
        return new AquaticCatalystChannelingRecipeBuilder(ingredient, powerAmount);
    }

    public static AquaticCatalystChannelingRecipeBuilder newRecipe(Ingredient ingredient, int powerAmount, int duration) {
        return new AquaticCatalystChannelingRecipeBuilder(ingredient, powerAmount, duration);
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
        return ingredient.getItems().length > 0 ? ingredient.getItems()[0].getItem() : Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        AquaticCatalystChannelingRecipe recipe = new AquaticCatalystChannelingRecipe(this.ingredient, this.powerAmount, this.duration);
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
