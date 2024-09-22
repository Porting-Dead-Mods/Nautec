package com.portingdeadmods.nautec.compat.jei;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.compat.jei.categories.AquaticCatalystChannelingRecipeCategory;
import com.portingdeadmods.nautec.compat.jei.categories.ItemEtchingRecipeCategory;
import com.portingdeadmods.nautec.compat.jei.categories.ItemTransformationRecipeCategory;
import com.portingdeadmods.nautec.compat.jei.categories.MixingRecipeCategory;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.nautec.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class NTJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ItemTransformationRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new AquaticCatalystChannelingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new ItemEtchingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new MixingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ItemTransformationRecipe> transformationRecipes = recipeManager.getAllRecipesFor(ItemTransformationRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<AquaticCatalystChannelingRecipe> channelingRecipes = recipeManager.getAllRecipesFor(AquaticCatalystChannelingRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<ItemEtchingRecipe> etchingRecipes = recipeManager.getAllRecipesFor(ItemEtchingRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<MixingRecipe> mixingRecipes = recipeManager.getAllRecipesFor(MixingRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        registration.addRecipes(ItemTransformationRecipeCategory.RECIPE_TYPE, transformationRecipes);
        registration.addRecipes(AquaticCatalystChannelingRecipeCategory.RECIPE_TYPE, channelingRecipes);
        registration.addRecipes(ItemEtchingRecipeCategory.RECIPE_TYPE, etchingRecipes);
        registration.addRecipes(MixingRecipeCategory.RECIPE_TYPE, mixingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.AQUATIC_CATALYST.get()),
                AquaticCatalystChannelingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTItems.ETCHING_ACID_BUCKET.get()),
                ItemEtchingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.MIXER.get()),
                MixingRecipeCategory.RECIPE_TYPE);
    }
}