package com.portingdeadmods.modjam.compat.jei;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.registries.MJBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemTransformationRecipeCategory implements IRecipeCategory<ItemTransformationRecipe> {
    private static final ResourceLocation BURN_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(ModJam.MODID,"container/furnace/empty_arrow");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "item_transformation");
    public static final RecipeType<ItemTransformationRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, ItemTransformationRecipe.class);

    private final IDrawable icon;
    private final IDrawable background;


    public ItemTransformationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(80, 16);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MJBlocks.PRISMARINE_RELAY.get()));
    }

    @Override
    public RecipeType<ItemTransformationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Item Transformation");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(ItemTransformationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        guiGraphics.blitSprite(BURN_PROGRESS_SPRITE, 28, 0, 24, 16);
    }
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ItemTransformationRecipe recipe, IFocusGroup focuses) {
        //Just one input slot, an arrow and an output slot
        builder.addSlot(RecipeIngredientRole.INPUT, 0,0 ).addItemStack(recipe.getIngredients().get(0).getItems()[0]);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 0).addItemStack(recipe.getResultItem(null));
    }


}
