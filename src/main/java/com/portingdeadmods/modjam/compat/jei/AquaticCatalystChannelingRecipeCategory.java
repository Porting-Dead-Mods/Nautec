package com.portingdeadmods.modjam.compat.jei;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.recipes.AquaticCatalystChannelingRecipe;
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import static com.portingdeadmods.modjam.compat.jei.ItemTransformationRecipeCategory.BURN_PROGRESS_SPRITE;

public class AquaticCatalystChannelingRecipeCategory implements IRecipeCategory<AquaticCatalystChannelingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "aquatic_catalyst_channeling");
    public static final RecipeType<AquaticCatalystChannelingRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, AquaticCatalystChannelingRecipe.class);
    private final IDrawable icon;
    private final IDrawable background;

    public AquaticCatalystChannelingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(88, 16);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MJBlocks.AQUATIC_CATALYST.get()));
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
        guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf(recipe.powerAmount()) + " AP", 56, 4, ChatFormatting.WHITE.getColor().byteValue());
        guiGraphics.blitSprite(BURN_PROGRESS_SPRITE, 28, 0, 24, 16);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AquaticCatalystChannelingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 0,0 ).addItemStack(recipe.getIngredients().get(0).getItems()[0]);
    }
}
