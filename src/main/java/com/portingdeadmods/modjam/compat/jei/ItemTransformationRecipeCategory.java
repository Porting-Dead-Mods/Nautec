package com.portingdeadmods.modjam.compat.jei;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.registries.MJBlocks;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemTransformationRecipeCategory implements IRecipeCategory<ItemTransformationRecipe> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "item_transformation");

    public static final String TEXTURE_GUI_PATH = "textures/jei/gui/";

    public static final String TEXTURE_GUI_VANILLA = TEXTURE_GUI_PATH + "gui_vanilla.png";

    public static final ResourceLocation RECIPE_GUI_VANILLA = ResourceLocation.fromNamespaceAndPath(ModIds.JEI_ID, TEXTURE_GUI_VANILLA);

    public static final RecipeType<ItemTransformationRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, ItemTransformationRecipe.class);

    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawable background;


    public ItemTransformationRecipeCategory(IGuiHelper helper) {
        this.arrow = helper.drawableBuilder(RECIPE_GUI_VANILLA, 82, 128, 24, 17)
                .buildAnimated(0, IDrawableAnimated.StartDirection.LEFT, false);
        this.background = helper.createBlankDrawable(80, 16);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MJBlocks.AQUATIC_CATALYST.get()));
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
    public void setRecipe(IRecipeLayoutBuilder builder, ItemTransformationRecipe recipe, IFocusGroup focuses) {
        //Just one input slot, an arrow and an output slot
        builder.addSlot(RecipeIngredientRole.INPUT, 0,0 ).addItemStack(recipe.getIngredients().get(0).getItems()[0]);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 0).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ItemTransformationRecipe recipe, IFocusGroup focuses) {
        // FUCK JEI FUCK JEI FUCK JEI
    }
}
