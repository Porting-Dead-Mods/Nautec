package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record MutationRecipeBuilder(ResourceKey<Bacteria> inputBacteria, ResourceKey<Bacteria> resultBacteria, Ingredient catalyst, float chance) implements NTRecipeBuilder {
    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        // Create the recipe, handling null fluidIngredient and resultFluid
        BacteriaMutationRecipe recipe = new BacteriaMutationRecipe(
                inputBacteria, resultBacteria, catalyst, chance
        );
        recipeOutput.accept(resourceLocation, recipe, null);
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        StringBuilder builder = new StringBuilder();
        StringBuilder valuesStr = new StringBuilder();
        Ingredient.Value values[] = catalyst.getValues();
        List<ItemStack> stacks = new ArrayList<>();

        for (Ingredient.Value value : values) {
            stacks.addAll(value.getItems());
        }

        stacks.forEach(stack -> {
            ResourceLocation itemLocation = BuiltInRegistries.ITEM.getKey(stack.getItem());
            valuesStr.append("_").append(itemLocation.getPath().replace(':', '-'));
        });

        builder.append(inputBacteria.location().toString().replace(':', '-') + "_" + resultBacteria.location().toString().replace(':', '-')).append(valuesStr.toString());
        save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, getName() + "/" + builder));
    }

    @Override
    public List<Ingredient> getIngredients() {
        return Collections.singletonList(catalyst);
    }

    @Override
    public String getName() {
        return BacteriaMutationRecipe.NAME;
    }
}
