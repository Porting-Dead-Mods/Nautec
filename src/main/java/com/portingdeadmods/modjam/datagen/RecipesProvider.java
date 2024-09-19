package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.modjam.datagen.recipeBuilder.AquaticCatalystChannelingRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.ItemEtchingRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.ItemTransformationRecipeBuilder;
import com.portingdeadmods.modjam.datagen.recipeBuilder.MixingRecipeBuilder;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

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
                .purity(3)
                .duration(100)
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(MJItems.VALVE.toStack())
                .ingredient(MJItems.ANCIENT_VALVE.toStack())
                .duration(200)
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(MJItems.GEAR.toStack())
                .ingredient(MJItems.RUSTY_GEAR.toStack())
                .duration(160)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(MJItems.RUSTY_GEAR.get()))
                .ingredients(IngredientWithCount.fromItemTag(ItemTags.ANVIL), IngredientWithCount.fromItemLike(MJItems.ATLANTIC_GOLD_NUGGET.get(), 3))
                .duration(60)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(MJItems.ATLANTIC_GOLD_NUGGET.get(), 3))
                .ingredients(IngredientWithCount.fromItemLike(MJItems.ATLANTIC_GOLD_INGOT.get(), 1))
                .duration(60)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(MJItems.CROWBAR.get(), 1))
                .ingredients(IngredientWithCount.fromItemLike(MJItems.DIVING_HELMET.get(), 1)
                        , IngredientWithCount.fromItemLike(MJItems.DIVING_CHESTPLATE.get(), 1)
                        , IngredientWithCount.fromItemLike(MJItems.DIVING_LEGGINGS.get(), 1)
                        , IngredientWithCount.fromItemLike(MJItems.DIVING_BOOTS.get(), 1))
                .duration(60)
                .save(pRecipeOutput);
    }
}
