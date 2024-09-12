package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.modjam.datagen.recipeBuilder.AquaticCatalystChannelingRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.ItemTransformationRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.MixingRecipeBuilder;
import com.portingdeadmods.modjam.registries.MJFluids;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider {
    public RecipesProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(MJItems.AQUARINE_STEEL_INGOT.get(), 1))
                .ingredient(new ItemStack(Items.IRON_INGOT))
                .save(pRecipeOutput);

        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(Items.NETHERITE_INGOT, 1))
                .ingredient(new ItemStack(Items.SNOWBALL))
                .save(pRecipeOutput);

        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(Items.KELP))
                .powerAmount(500)
                .purity(0)
                .duration(100)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(MJItems.ELECTROLYTE_ALGAE_SERUM_VIAL.toStack())
                .ingredients(IngredientWithCount.fromItemLike(MJItems.ATLANTIC_GOLD_NUGGET, 3), IngredientWithCount.fromItemTag(ItemTags.ANVIL))
                .fluidIngredient(new FluidStack(MJFluids.SALT_WATER_SOURCE.get(), 100))
                .fluidResult(new FluidStack(Fluids.LAVA, 200))
                .duration(60)
                .save(pRecipeOutput);
    }
}
