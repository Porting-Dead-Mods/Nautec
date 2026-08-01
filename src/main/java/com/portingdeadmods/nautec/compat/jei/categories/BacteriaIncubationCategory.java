package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.BacteriaIncubationRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BacteriaIncubationCategory extends BacteriaCategory<BacteriaIncubationRecipe> {
    static final Identifier SINGLE_SLOT_SPRITE = Identifier.fromNamespaceAndPath(Nautec.MODID, "container/furnace/empty_slot");
    static final Identifier RIGHT_ARROW_SPRITE = Nautec.rl("container/incubator/progress_arrow_off");
    public static final Identifier UID = Nautec.rl(BacteriaIncubationRecipe.NAME);
    public static final IRecipeType<BacteriaIncubationRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, BacteriaIncubationRecipe.class);

    private static final int GAP = 5;
    private static final int SLOT_SIZE = 18;
    private static final int ARROW_WIDTH = 79;
    private static final int Y_GAP_BETWEEN_IN_CATA = 29;
    private static final int GAP_BETWEEN_SLOT_ARROW = 3;

    private static final int DRAWABLE_WIDTH = GAP * 2 + SLOT_SIZE * 2 + ARROW_WIDTH + GAP_BETWEEN_SLOT_ARROW * 2;
    private static final int DRAWABLE_HEIGHT = GAP * 2 + SLOT_SIZE + Y_GAP_BETWEEN_IN_CATA + 8;

    public BacteriaIncubationCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.bacteria_incubation"),
                helper.createDrawableItemStack(new ItemStack(NTBlocks.INCUBATOR.get())),
                DRAWABLE_WIDTH,
                DRAWABLE_HEIGHT);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BacteriaIncubationRecipe recipe, IFocusGroup focuses) {
        addBacteriaSlot(recipe, DRAWABLE_WIDTH / 2 - SLOT_SIZE / 2 + 1, GAP + Y_GAP_BETWEEN_IN_CATA - 22, recipe.bacteria());

        builder.addSlot(RecipeIngredientRole.INPUT, DRAWABLE_WIDTH / 2 - SLOT_SIZE / 2 + 2, GAP + Y_GAP_BETWEEN_IN_CATA)
                .setBackground(NTJeiUtil.sprite(SINGLE_SLOT_SPRITE, 18, 18), -1, -1)
                .add(recipe.nutrient());
    }

    @Override
    public void draw(@NotNull BacteriaIncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        NTJeiUtil.blitSprite(guiGraphics, RIGHT_ARROW_SPRITE, GAP + SLOT_SIZE + GAP_BETWEEN_SLOT_ARROW + 17, GAP - 2, 46, 29);

        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, BacteriaIncubationRecipe recipe, IFocusGroup focuses) {
        int fontSize = Minecraft.getInstance().font.lineHeight;
        String chanceText = recipe.consumeChance() * 100 + "%";
        String growthText = "Growth: " + recipe.growth().toString();

        builder.addText(Component.literal(chanceText), getWidth() / 2, fontSize)
                .setPosition(getWidth() / 2 + 15, getHeight() - 26)
                .setColor(0xFF808080)
                .setShadow(false);
        builder.addText(Component.literal(growthText), getWidth(), fontSize)
                .setPosition(0, getHeight() - 9)
                .setTextAlignment(HorizontalAlignment.CENTER)
                .setColor(0xFF808080)
                .setShadow(false);
    }
}
