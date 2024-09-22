package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.datagen.recipeBuilder.AquaticCatalystChannelingRecipeBuilder;
import com.portingdeadmods.nautec.datagen.recipeBuilder.ItemEtchingRecipeBuilder;
import com.portingdeadmods.nautec.datagen.recipeBuilder.ItemTransformationRecipeBuilder;
import com.portingdeadmods.nautec.datagen.recipeBuilder.MixingRecipeBuilder;
import com.portingdeadmods.nautec.registries.NTItems;
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

        ItemTransformationRecipeBuilder.newRecipe(new ItemStack(NTItems.AQUARINE_STEEL_INGOT.get(), 1))
                .ingredient(new ItemStack(Items.IRON_INGOT))
                .purity(3)
                .duration(100)
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(NTItems.VALVE.toStack())
                .ingredient(NTItems.ANCIENT_VALVE.toStack())
                .duration(200)
                .save(pRecipeOutput);

        ItemEtchingRecipeBuilder.newRecipe(NTItems.GEAR.toStack())
                .ingredient(NTItems.RUSTY_GEAR.toStack())
                .duration(160)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(NTItems.RUSTY_GEAR.get()))
                .ingredients(IngredientWithCount.fromItemTag(ItemTags.ANVIL), IngredientWithCount.fromItemLike(NTItems.ATLANTIC_GOLD_NUGGET.get(), 3))
                .duration(60)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(NTItems.ATLANTIC_GOLD_NUGGET.get(), 3))
                .ingredients(IngredientWithCount.fromItemLike(NTItems.ATLANTIC_GOLD_INGOT.get(), 1))
                .duration(60)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(NTItems.CROWBAR.get(), 1))
                .ingredients(IngredientWithCount.fromItemLike(NTItems.DIVING_HELMET.get(), 1)
                        , IngredientWithCount.fromItemLike(NTItems.DIVING_CHESTPLATE.get(), 1)
                        , IngredientWithCount.fromItemLike(NTItems.DIVING_LEGGINGS.get(), 1)
                        , IngredientWithCount.fromItemLike(NTItems.DIVING_BOOTS.get(), 1))
                .duration(60)
                .save(pRecipeOutput);

        MixingRecipeBuilder.newRecipe(new ItemStack(NTItems.EAS_BUCKET.get(), 1))
                .ingredients(IngredientWithCount.fromItemLike(Items.DRIED_KELP, 4)
                        , IngredientWithCount.fromItemLike(Items.SLIME_BALL, 2)
                        , IngredientWithCount.fromItemLike(Items.PRISMARINE_CRYSTALS, 1)
                        , IngredientWithCount.fromItemLike(Items.SEAGRASS, 5))
                .duration(200)
                .save(pRecipeOutput);

    }
}
