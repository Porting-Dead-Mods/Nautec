package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BacteriaMutationsCategory extends BacteriaCategory<BacteriaMutationRecipe> {
    static final Identifier SINGLE_SLOT_SPRITE = Identifier.fromNamespaceAndPath(Nautec.MODID, "container/furnace/empty_slot");
    static final Identifier RIGHT_ARROW_SPRITE = Nautec.rl("container/mutator/progress_arrow_off");
    public static final Identifier UID = Nautec.rl(BacteriaMutationRecipe.NAME);
    public static final IRecipeType<BacteriaMutationRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, BacteriaMutationRecipe.class);

    private static final int GAP = 5;
    private static final int SLOT_SIZE = 18;
    private static final int ARROW_WIDTH = 62;
    private static final int ARROW_HEIGHT = 14;
    private static final int Y_GAP_BETWEEN_IN_CATA = 29;
    private static final int GAP_BETWEEN_SLOT_ARROW = 3;

    private static final int DRAWABLE_WIDTH = GAP * 2 + SLOT_SIZE * 2 + ARROW_WIDTH + GAP_BETWEEN_SLOT_ARROW * 2;
    private static final int DRAWABLE_HEIGHT = GAP * 2 + SLOT_SIZE + Y_GAP_BETWEEN_IN_CATA + 8;

    public BacteriaMutationsCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.bacteria_mutations"),
                helper.createDrawableItemStack(new ItemStack(NTBlocks.MUTATOR.get())),
                DRAWABLE_WIDTH,
                DRAWABLE_HEIGHT);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BacteriaMutationRecipe recipe, IFocusGroup focuses) {
        addBacteriaSlot(recipe, GAP - 1, GAP + 7, recipe.inputBacteria());
        addBacteriaSlot(recipe, DRAWABLE_WIDTH - GAP - SLOT_SIZE, GAP + 7, recipe.resultBacteria());

        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).add(recipe.getInputDish());

        builder.addSlot(RecipeIngredientRole.INPUT, DRAWABLE_WIDTH / 2 - SLOT_SIZE / 2 + 1, GAP + 8 + Y_GAP_BETWEEN_IN_CATA)
                .setBackground(NTJeiUtil.sprite(SINGLE_SLOT_SPRITE, 18, 18), -1, -1)
                .add(recipe.catalyst());
    }

    @Override
    public void draw(@NotNull BacteriaMutationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        NTJeiUtil.blitSprite(guiGraphics, RIGHT_ARROW_SPRITE, GAP + SLOT_SIZE + GAP_BETWEEN_SLOT_ARROW, GAP + 8 - 1, ARROW_WIDTH, ARROW_HEIGHT);

        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, BacteriaMutationRecipe recipe, IFocusGroup focuses) {
        Font font = Minecraft.getInstance().font;
        String purityString = recipe.chance() + "%";

        int width = font.width(purityString);
        builder.addText(Component.literal(purityString), width, font.lineHeight)
                .setPosition(DRAWABLE_WIDTH - GAP - SLOT_SIZE - (width - 18) / 2, GAP + 8 - 3 - font.lineHeight)
                .setColor(0xFF808080)
                .setShadow(false);
    }
}
