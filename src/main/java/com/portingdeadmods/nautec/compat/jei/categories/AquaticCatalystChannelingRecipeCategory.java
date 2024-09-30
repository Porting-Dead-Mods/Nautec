package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
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
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AquaticCatalystChannelingRecipeCategory implements IRecipeCategory<AquaticCatalystChannelingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "aquatic_catalyst_channeling");
    public static final RecipeType<AquaticCatalystChannelingRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, AquaticCatalystChannelingRecipe.class);
    private final IDrawable icon;
    private final IDrawable background;

    public AquaticCatalystChannelingRecipeCategory(IGuiHelper helper) {
        Font font = Minecraft.getInstance().font;
        this.background = helper.createBlankDrawable(136, 24 + 4 * font.lineHeight);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(NTBlocks.AQUATIC_CATALYST.get()));
    }

    @Override
    public RecipeType<AquaticCatalystChannelingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Aquatic Catalyst Channeling");
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
    public void draw(AquaticCatalystChannelingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        int fontSize = font.lineHeight;
        Component[] text = new Component[]{
                Component.literal("Power per tick: " + recipe.powerAmount() / recipe.duration() + " AP/t"),
                Component.literal("Total Power amount: " + recipe.powerAmount() + " AP"),
                Component.literal("Duration: "+recipe.duration()),
                Component.literal("Purity: "+recipe.purity()),
        };

        for (int i = 0; i < text.length; i++) {
            guiGraphics.drawCenteredString(font, text[i], 66, 18 + i * fontSize, ChatFormatting.WHITE.getColor().byteValue());
        }

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AquaticCatalystChannelingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, getWidth() / 2 - 8, 0).addIngredients(recipe.getIngredients().get(0));
    }
}
