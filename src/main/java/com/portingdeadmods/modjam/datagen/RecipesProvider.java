package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.content.recipes.MixingRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.modjam.datagen.recipeBuilder.AquaticCatalystChannelingRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.ItemEtchingRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.ItemTransformationRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.MixingRecipeBuilder;
import com.portingdeadmods.modjam.registries.MJFluids;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider {
    public RecipesProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        AquaticCatalystChannelingRecipeBuilder.newRecipe(Ingredient.of(Items.KELP))
                .powerAmount(500)
                .purity(0)
                .duration(100)
                .save(pRecipeOutput);

        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(MJItems.AQUARINE_STEEL_INGOT.get(), 1))
                .ingredient(new ItemStack(Items.IRON_INGOT))
                .save(pRecipeOutput);

        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(Items.NETHERITE_INGOT, 1))
                .ingredient(new ItemStack(Items.SNOWBALL))
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(new ItemStack(Items.DIAMOND, 1))
                .ingredient(new ItemStack(Items.EMERALD))
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(MJItems.RUSTY_GEAR.get()))
                .ingredients(IngredientWithCount.fromItemTag(ItemTags.ANVIL), IngredientWithCount.fromItemLike(MJItems.ATLANTIC_GOLD_NUGGET.get(), 3))
                .fluidIngredient(new FluidStack(Fluids.WATER, 100))
                .fluidResult(FluidStack.EMPTY)
                .duration(60)
                .save(pRecipeOutput);
        }
}
