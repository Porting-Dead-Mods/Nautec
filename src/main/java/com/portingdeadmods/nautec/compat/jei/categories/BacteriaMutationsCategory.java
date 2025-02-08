package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
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
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BacteriaMutationsCategory extends BacteriaCategory<BacteriaMutationRecipe> {
    static final ResourceLocation SINGLE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/furnace/empty_slot");
    static final ResourceLocation BACTERIA_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/bacteria_slot");
    static final ResourceLocation RIGHT_ARROW_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "container/mutator/progress_arrow_off");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, BacteriaMutationRecipe.NAME);
    public static final RecipeType<BacteriaMutationRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, BacteriaMutationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private final int gap = 5;
    private final int slotSize = 18;
    private final int arrowWidth = 79;
    private final int arrowHeight = 24;
    private final int gapBetweenInOut = 107;
    private final int YGapBetweenInCata = 29;
    private final int gapBetweenSlotArrow = 3;

    private final int drawableWidth = gap * 2 + slotSize * 2 + arrowWidth + gapBetweenSlotArrow * 2; // 2 Slots + Arrow
    private final int drawableHeight = gap * 2 + slotSize + YGapBetweenInCata + 8; // 8 Reserved for the Chance text

    public BacteriaMutationsCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(drawableWidth, drawableHeight);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(NTBlocks.MUTATOR.get()));
    }

    @Override
    public RecipeType<BacteriaMutationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Bacteria Mutations");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BacteriaMutationRecipe recipe, IFocusGroup focuses) {

        addBacteriaSlot(recipe, gap - 1, gap + 7, recipe.inputBacteria());
        addBacteriaSlot(recipe, gap + gapBetweenInOut - 2, gap + 7, recipe.resultBacteria());

        builder.addSlot(RecipeIngredientRole.INPUT, drawableWidth / 2 - slotSize / 2 + 1, gap + 8 + YGapBetweenInCata)
                .addIngredients(recipe.catalyst());
    }

    @Override
    public void draw(@NotNull BacteriaMutationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, gap - 1, gap + 8 - 1, 18, 18);
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, gap + gapBetweenInOut - 1 - 1, gap + 8 - 1, 18, 18);
        guiGraphics.blitSprite(RIGHT_ARROW_SPRITE, gap + slotSize + gapBetweenSlotArrow, gap + 8 - 1 - 4, arrowWidth, arrowHeight);

        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, drawableWidth / 2 - slotSize / 2 - 1 + 1, gap + 8 + YGapBetweenInCata - 1, 18, 18);

        Font font = Minecraft.getInstance().font;
        String purityString = recipe.chance() + "%";

        int width = font.width(purityString);
        guiGraphics.drawString(font, purityString, gap + gapBetweenInOut - (width - 18) / 2 - 1, gap + 8 - 3 - font.lineHeight, 0xFF808080, false);

        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
    }
}
