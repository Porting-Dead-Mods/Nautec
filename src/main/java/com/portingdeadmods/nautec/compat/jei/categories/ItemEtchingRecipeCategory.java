package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.nautec.registries.NTFluids;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
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

public class ItemEtchingRecipeCategory extends AbstractRecipeCategory<ItemEtchingRecipe> {
    static final Identifier BURN_PROGRESS_SPRITE = Identifier.fromNamespaceAndPath(Nautec.MODID, "container/furnace/empty_arrow");
    public static final Identifier UID = Nautec.rl("item_etching");
    public static final IRecipeType<ItemEtchingRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, ItemEtchingRecipe.class);

    public ItemEtchingRecipeCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.item_etching"),
                helper.createDrawableItemStack(new ItemStack(NTFluids.ETCHING_ACID.getBucket())),
                80,
                28);
    }

    @Override
    public void draw(ItemEtchingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        NTJeiUtil.blitSprite(guiGraphics, BURN_PROGRESS_SPRITE, 28, 0, 24, 16);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ItemEtchingRecipe recipe, IFocusGroup focuses) {
        builder.addText(Component.literal(((float) recipe.duration() / 20) + "s"), getWidth() / 2, Minecraft.getInstance().font.lineHeight)
                .setPosition(0, 20)
                .setColor(0xFF808080)
                .setShadow(false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ItemEtchingRecipe recipe, IFocusGroup focuses) {
        NTJeiUtil.addIngredientWithCount(builder.addSlot(RecipeIngredientRole.INPUT, 0, 0), recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 0).add(recipe.result());
    }
}
