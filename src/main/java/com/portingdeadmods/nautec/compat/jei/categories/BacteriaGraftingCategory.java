package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.data.maps.BacteriaObtainValue;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.Utils;
import it.unimi.dsi.fastutil.Pair;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class BacteriaGraftingCategory implements IRecipeCategory<BacteriaGraftingCategory.GraftingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, GraftingRecipe.NAME);
    public static final RecipeType<GraftingRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, GraftingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public BacteriaGraftingCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(132, 42);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(NTItems.GRAFTING_TOOL.get()));
    }

    @Override
    public RecipeType<GraftingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Bacteria Grafting");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GraftingRecipe recipe, IFocusGroup focuses) {
        int y = 6;

        builder.addInputSlot(0, getHeight() / 2 - 9 + y).addItemLike(NTItems.PETRI_DISH);

        ItemStack stack = NTItems.PETRI_DISH.toStack();
        IBacteriaStorage bacteriaStorage = stack.getCapability(NTCapabilities.BacteriaStorage.ITEM);
        bacteriaStorage.setBacteria(0, BacteriaInstance.withMaxStats(recipe.val.bacteria(), Minecraft.getInstance().level.registryAccess()));
        builder.addOutputSlot(getWidth() - 18, getHeight() / 2 - 9 + y).addItemStack(stack);

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, getWidth() / 2 - 9, getHeight() / 2 - 18 + y).addItemLike(NTItems.GRAFTING_TOOL);
        builder.addSlot(RecipeIngredientRole.INPUT, getWidth() / 2 - 9, getHeight() / 2 + y).addItemLike(recipe.block());
    }

    @Override
    public void draw(GraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;

        guiGraphics.drawString(font, Component.literal("Only In: ").append(recipe.val.biome().location().toString()),0, 0, ChatFormatting.DARK_GRAY.getColor(), false);
    }

    public record GraftingRecipe(Block block, BacteriaObtainValue val) {
        public static final String NAME = "grafting";
    }
}
