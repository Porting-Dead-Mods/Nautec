package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.datagen.recipeBuilder.*;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.neoforge.fluids.FluidStack;
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

        MixingRecipeBuilder.newRecipe(ItemStack.EMPTY)
                .ingredients(IngredientWithCount.fromItemLike(Items.DRIED_KELP, 4)
                        , IngredientWithCount.fromItemLike(Items.SLIME_BALL, 2)
                        , IngredientWithCount.fromItemLike(Items.PRISMARINE_CRYSTALS, 1)
                        , IngredientWithCount.fromItemLike(Items.SEAGRASS, 5))
                .duration(200)
                .fluidIngredient(new FluidStack(NTFluids.SALT_WATER_SOURCE.get(), 500))
                .fluidResult(new FluidStack(NTFluids.EAS_SOURCE.get(), 500))
                .save(pRecipeOutput);

        AugmentationRecipeBuilder.newRecipe(NTAugments.DOLPHIN_FIN.get())
                .ingredients(NTItems.DOLPHIN_FIN.get(), IngredientWithCount.fromItemLike(NTItems.DOLPHIN_FIN.get()))
                .save(pRecipeOutput);

        ItemStack divingChestplate = NTItems.DIVING_CHESTPLATE.get().getDefaultInstance();
        divingChestplate.set(NTDataComponents.OXYGEN,600);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,divingChestplate)
                .pattern("GGG")
                .pattern("GDG")
                .pattern("GGG")
                .unlockedBy("has_item",has(NTItems.DIVING_CHESTPLATE.get()))
                .define('G',NTItems.AIR_BOTTLE.get())
                .define('D',NTItems.DIVING_CHESTPLATE.get())
                .save(pRecipeOutput);
    }
}
