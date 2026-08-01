package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ItemTransformationRecipeCategory extends AbstractRecipeCategory<ItemTransformationRecipe> {
    static final Identifier BURN_PROGRESS_SPRITE = Nautec.rl("container/furnace/empty_arrow");
    public static final Identifier UID = Nautec.rl("item_transformation");
    public static final IRecipeType<ItemTransformationRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, ItemTransformationRecipe.class);

    public ItemTransformationRecipeCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.item_transformation"),
                helper.createDrawableItemStack(new ItemStack(NTBlocks.PRISMARINE_RELAY.get())),
                80,
                28);
    }

    @Override
    public void draw(ItemTransformationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        NTJeiUtil.blitSprite(guiGraphics, BURN_PROGRESS_SPRITE, 28, 0, 24, 16);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ItemTransformationRecipe recipe, IFocusGroup focuses) {
        int fontSize = Minecraft.getInstance().font.lineHeight;
        builder.addText(Component.literal(((float) recipe.duration() / 20) + "s"), getWidth() / 2, fontSize)
                .setPosition(0, 20)
                .setColor(0xFF808080)
                .setShadow(false);
        builder.addText(Component.literal(recipe.purity() + " purity"), getWidth(), fontSize)
                .setPosition(0, 20)
                .setTextAlignment(HorizontalAlignment.RIGHT)
                .setColor(0xFF808080)
                .setShadow(false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ItemTransformationRecipe recipe, IFocusGroup focuses) {
        NTJeiUtil.addIngredientWithCount(builder.addSlot(RecipeIngredientRole.INPUT, 0, 0), recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 0).add(recipe.result());
    }
}
