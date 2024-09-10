package com.portingdeadmods.modjam.compat.jei;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.registries.MJBlocks;
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
public class MJJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ModJam.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ItemTransformationRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ItemTransformationRecipe> transformationRecipes = recipeManager.getAllRecipesFor(ItemTransformationRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(ItemTransformationRecipeCategory.RECIPE_TYPE, transformationRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(MJBlocks.AQUATIC_CATALYST.get()),
                ItemTransformationRecipeCategory.RECIPE_TYPE);
    }
}
