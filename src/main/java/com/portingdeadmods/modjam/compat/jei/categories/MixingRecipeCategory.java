package com.portingdeadmods.modjam.compat.jei.categories;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.content.recipes.MixingRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.RecipeUtils;
import com.portingdeadmods.modjam.registries.MJBlocks;
import com.portingdeadmods.modjam.registries.MJRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MixingRecipeCategory implements IRecipeCategory<MixingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, MixingRecipe.NAME);
    public static final RecipeType<MixingRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, MixingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MixingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(80, 128);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MJBlocks.PRISMARINE_RELAY.get()));
    }

    @Override
    public RecipeType<MixingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Mixing");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MixingRecipe recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, i * 20, 0).addIngredients(RecipeUtils.iWCToIngredientSaveCount(recipe.ingredients().get(i)));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 0, 32).addItemStack(recipe.result());
        builder.addSlot(RecipeIngredientRole.INPUT, 0, 64).addFluidStack(recipe.fluidIngredient().getFluid(), recipe.fluidIngredient().getAmount());
        builder.addSlot(RecipeIngredientRole.INPUT, 0, 96).addFluidStack(recipe.fluidResult().getFluid(), recipe.fluidResult().getAmount());
    }
}
