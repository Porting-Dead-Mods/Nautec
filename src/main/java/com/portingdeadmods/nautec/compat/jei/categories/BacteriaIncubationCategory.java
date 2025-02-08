package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.content.recipes.BacteriaIncubationRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.utils.GuiUtils;
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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BacteriaIncubationCategory extends BacteriaCategory<BacteriaIncubationRecipe> {
    static final ResourceLocation SINGLE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/furnace/empty_slot");
    static final ResourceLocation RIGHT_ARROW_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "container/incubator/progress_arrow_off");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, BacteriaIncubationRecipe.NAME);
    public static final RecipeType<BacteriaIncubationRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, BacteriaIncubationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private final int gap = 5;
    private final int slotSize = 18;
    private final int arrowWidth = 79;
    private final int YGapBetweenInCata = 29;
    private final int gapBetweenSlotArrow = 3;

    private final int drawableWidth = gap * 2 + slotSize * 2 + arrowWidth + gapBetweenSlotArrow * 2; // 2 Slots + Arrow
    private final int drawableHeight = gap * 2 + slotSize + YGapBetweenInCata + 8; // 8 Reserved for the Chance text

    public BacteriaIncubationCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(drawableWidth, drawableHeight);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(NTBlocks.INCUBATOR.get()));
    }

    @Override
    public RecipeType<BacteriaIncubationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Bacteria Incubation");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BacteriaIncubationRecipe recipe, IFocusGroup focuses) {
        addBacteriaSlot(recipe, drawableWidth / 2 - slotSize / 2 + 1, gap + YGapBetweenInCata - 22, recipe.bacteria());

        builder.addSlot(RecipeIngredientRole.INPUT, drawableWidth / 2 - slotSize / 2 + 2, gap + YGapBetweenInCata)
                .addIngredients(recipe.nutrient());
    }

    @Override
    public void draw(@NotNull BacteriaIncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blitSprite(RIGHT_ARROW_SPRITE, gap + slotSize + gapBetweenSlotArrow + 17, gap - 2, 46, 29);

        // Catalyst Slot
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, drawableWidth / 2 - slotSize / 2 - 1 + 2, gap + YGapBetweenInCata - 1, 18, 18);

        Font font = Minecraft.getInstance().font;
        String chanceText = recipe.consumeChance() * 100 + "%";
        String growthText = "Growth: " + recipe.growth().toString();

        guiGraphics.drawString(font, chanceText, getWidth() / 2 + 15, getHeight() - 26, 0xFF808080, false);
        int growthWidth = font.width(growthText);
        guiGraphics.drawString(font, growthText, getWidth() / 2 - growthWidth / 2, getHeight() - 9, 0xFF808080, false);

        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }

}
