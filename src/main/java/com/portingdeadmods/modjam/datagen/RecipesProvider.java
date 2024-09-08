package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.datagen.recipe_builder.ItemTransformationRecipeBuilder;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider {
    public RecipesProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(MJItems.AQUARINE_STEEL.get(), 1))
                .ingredients(new ItemStack(Items.IRON_INGOT, 1))
                .save(pRecipeOutput);

        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(Items.NETHERITE_INGOT, 1))
                .ingredients(new ItemStack(Items.SNOWBALL, 1))
                .save(pRecipeOutput);
    }
}
