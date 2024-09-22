package com.portingdeadmods.modjam.datagen.recipeBuilder;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AugmentationRecipeBuilder implements MJRecipeBuilder {
    private Ingredient augmentItem;
    private List<Ingredient> robotArms;


    @Override
    public List<Ingredient> getIngredients() {
        return List.of();
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return null;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return null;
    }

    @Override
    public Item getResult() {
        return null;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {

    }
}
