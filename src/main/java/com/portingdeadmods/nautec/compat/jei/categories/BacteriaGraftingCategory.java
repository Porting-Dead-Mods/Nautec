package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.data.maps.BacteriaObtainValue;
import com.portingdeadmods.nautec.registries.NTItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class BacteriaGraftingCategory extends AbstractRecipeCategory<BacteriaGraftingCategory.GraftingRecipe> {
    public static final Identifier UID = Nautec.rl(GraftingRecipe.NAME);
    public static final IRecipeType<GraftingRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, GraftingRecipe.class);

    public BacteriaGraftingCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.bacteria_grafting"),
                helper.createDrawableItemStack(new ItemStack(NTItems.GRAFTING_TOOL.get())),
                132,
                42);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GraftingRecipe recipe, IFocusGroup focuses) {
        int y = 6;

        builder.addInputSlot(0, getHeight() / 2 - 9 + y).add(NTItems.PETRI_DISH);

        ItemStack stack = NTItems.PETRI_DISH.toStack();
        IBacteriaStorage bacteriaStorage = NTCapabilities.BacteriaStorage.ITEM.getCapability(stack, null);
        bacteriaStorage.setBacteria(0, BacteriaInstance.withMaxStats(recipe.val.bacteria(), Minecraft.getInstance().level.registryAccess()));
        builder.addOutputSlot(getWidth() - 18, getHeight() / 2 - 9 + y).add(stack);

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, getWidth() / 2 - 9, getHeight() / 2 - 18 + y).add(NTItems.GRAFTING_TOOL);
        builder.addSlot(RecipeIngredientRole.INPUT, getWidth() / 2 - 9, getHeight() / 2 + y).add(recipe.block());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, GraftingRecipe recipe, IFocusGroup focuses) {
        builder.addText(Component.literal("Only In: ").append(recipe.val.biome().location().toString()), getWidth(), Minecraft.getInstance().font.lineHeight)
                .setPosition(0, 0)
                .setColor(0xFF000000 | ChatFormatting.DARK_GRAY.getColor())
                .setShadow(false);
    }

    public record GraftingRecipe(Block block, BacteriaObtainValue val) {
        public static final String NAME = "grafting";
    }
}
