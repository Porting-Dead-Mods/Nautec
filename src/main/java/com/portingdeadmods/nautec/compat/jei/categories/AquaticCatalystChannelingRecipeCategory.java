package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class AquaticCatalystChannelingRecipeCategory extends AbstractRecipeCategory<AquaticCatalystChannelingRecipe> {
    public static final Identifier UID = Nautec.rl("aquatic_catalyst_channeling");
    public static final IRecipeType<AquaticCatalystChannelingRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, AquaticCatalystChannelingRecipe.class);

    public AquaticCatalystChannelingRecipeCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.aquatic_catalyst_channeling"),
                helper.createDrawableItemStack(new ItemStack(NTBlocks.AQUATIC_CATALYST.get())),
                136,
                24 + 4 * Minecraft.getInstance().font.lineHeight);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, AquaticCatalystChannelingRecipe recipe, IFocusGroup focuses) {
        int fontSize = Minecraft.getInstance().font.lineHeight;
        Component[] text = new Component[]{
                Component.literal("Power per tick: " + recipe.powerAmount() / recipe.duration() + " AP/t"),
                Component.literal("Total Power amount: " + recipe.powerAmount() + " AP"),
                Component.literal("Duration: " + recipe.duration()),
                Component.literal("Purity: " + recipe.purity()),
        };

        for (int i = 0; i < text.length; i++) {
            builder.addText(text[i], getWidth(), fontSize)
                    .setPosition(0, 18 + i * fontSize)
                    .setTextAlignment(HorizontalAlignment.CENTER)
                    .setColor(0xFFFFFFFF)
                    .setShadow(true);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AquaticCatalystChannelingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, getWidth() / 2 - 8, 0).add(recipe.ingredient());
    }
}
