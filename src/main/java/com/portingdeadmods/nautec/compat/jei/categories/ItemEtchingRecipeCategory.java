package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.nautec.registries.MJItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemEtchingRecipeCategory implements IRecipeCategory<ItemEtchingRecipe> {
    static final ResourceLocation BURN_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/furnace/empty_arrow");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "item_etching");
    public static final RecipeType<ItemEtchingRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, ItemEtchingRecipe.class);

    private final IDrawable icon;
    private final IDrawable background;


    public ItemEtchingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(80, 28);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MJItems.ETCHING_ACID_BUCKET.get()));
    }

    @Override
    public RecipeType<ItemEtchingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Item Etching");
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
    public void draw(ItemEtchingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        guiGraphics.blitSprite(BURN_PROGRESS_SPRITE, 28, 0, 24, 16);
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawString(font, ((float) recipe.duration() / 20)+"s", 0, 20, 0xFF808080, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ItemEtchingRecipe recipe, IFocusGroup focuses) {
        //Just one input slot, an arrow and an output slot
        builder.addSlot(RecipeIngredientRole.INPUT, 0,0 ).addItemStack(recipe.getIngredients().get(0).getItems()[0]);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 0).addItemStack(recipe.getResultItem(null));
    }

    private boolean shouldRenderTooltip(double mouseX, double mouseY) {
        int width = 24;
        int height = 16;
        int x = 28;
        int y = 0;
        boolean matchesOnX = mouseX > x && mouseX < x + width;
        boolean matchesOnY = mouseY > y && mouseY < y + height;
        return matchesOnX && matchesOnY;
    }


}