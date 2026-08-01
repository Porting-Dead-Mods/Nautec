package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AquaticCatalystChannelingRecipeBuilder implements NTRecipeBuilder {
    private final Ingredient ingredient;
    private int powerAmount;
    private float purity;
    private int duration;

    private AquaticCatalystChannelingRecipeBuilder(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public static AquaticCatalystChannelingRecipeBuilder newRecipe(Ingredient ingredient) {
        return new AquaticCatalystChannelingRecipeBuilder(ingredient);
    }

    public AquaticCatalystChannelingRecipeBuilder powerAmount(int powerAmount) {
        this.powerAmount = powerAmount;
        return this;
    }

    public AquaticCatalystChannelingRecipeBuilder purity(float purity) {
        this.purity = purity;
        return this;
    }

    public AquaticCatalystChannelingRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
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
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> key) {
        AquaticCatalystChannelingRecipe recipe = new AquaticCatalystChannelingRecipe(this.ingredient, this.powerAmount, this.purity, this.duration);
        recipeOutput.accept(key, recipe, null);
    }

    @Override
    public List<Ingredient> getIngredients() {
        return List.of(this.ingredient);
    }

    @Override
    public String getName() {
        return AquaticCatalystChannelingRecipe.NAME;
    }
}
