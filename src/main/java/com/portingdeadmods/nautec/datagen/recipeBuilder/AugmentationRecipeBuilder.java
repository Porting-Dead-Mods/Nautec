package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.content.items.RobotArmItem;
import com.portingdeadmods.nautec.content.recipes.AugmentationRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AugmentationRecipeBuilder implements NTRecipeBuilder {
    private Ingredient augmentItem;
    private List<Ingredient> robotArms;
    private AugmentType<?> augmentType;

    public static AugmentationRecipeBuilder newRecipe(AugmentType<?> augmentType) {
        AugmentationRecipeBuilder builder = new AugmentationRecipeBuilder();
        builder.augmentType = augmentType;
        return builder;
    }

    public AugmentationRecipeBuilder robotArms(RobotArmItem ...items) {
        this.robotArms = Stream.of(items).map(Ingredient::of).toList();
        return this;
    }

    public AugmentationRecipeBuilder augmentItem(Item item) {
        this.augmentItem = Ingredient.of(item);
        return this;
    }

    @Override
    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>(robotArms);
        ingredients.addFirst(augmentItem);
        return ingredients;
    }

    @Override
    public String getName() {
        return AugmentationRecipe.NAME;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        recipeOutput.accept(id, new AugmentationRecipe(augmentItem, 1, augmentType), null);
    }
}
