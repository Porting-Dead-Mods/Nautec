package com.portingdeadmods.modjam.compat.jei.categories;

import com.mojang.datafixers.util.Pair;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import com.portingdeadmods.modjam.content.recipes.MixingRecipe;
import com.portingdeadmods.modjam.content.recipes.utils.RecipeUtils;
import com.portingdeadmods.modjam.registries.MJBlocks;
import com.portingdeadmods.modjam.registries.MJRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.ISlottedWidgetFactory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MixingRecipeCategory implements IRecipeCategory<MixingRecipe> {
    static final ResourceLocation SINGLE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(ModJam.MODID,"container/furnace/empty_slot");
    static final ResourceLocation DOWN_ARROW_SPRITE = ResourceLocation.fromNamespaceAndPath(ModJam.MODID,"container/furnace/down_arrow");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, MixingRecipe.NAME);
    public static final RecipeType<MixingRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, MixingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MixingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(80, 66);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MJBlocks.MIXER.get()));
    }

    @Override
    public RecipeType<MixingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Mixing");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    private final List<Pair<Integer, Integer>> slotPositions = new ArrayList<>();

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MixingRecipe recipe, IFocusGroup focuses) {
        slotPositions.clear(); // Clear previous slot positions

        int maxSlots = 4;
        int slotSize = 18; // Assuming each slot is 18x18 pixels
        int gap = 2;       // Assuming a small gap between the slots (optional)

        // Check if there are ingredients
        if (!recipe.getIngredients().isEmpty()) {
            // Calculate total width needed for the slots
            int totalSlotWidth = (Math.min(recipe.getIngredients().size(), maxSlots) * slotSize)
                    + ((Math.min(recipe.getIngredients().size(), maxSlots) - 1) * gap);

            // Calculate the starting X position to center the slots in an 80 pixel wide area
            int startX = (80 - totalSlotWidth) / 2;

            // Input slots
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                int x = startX + i * (slotSize + gap); // Centered X position for each slot
                int y = 0; // All inputs are at the same Y level
                builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                        .addIngredients(RecipeUtils.iWCToIngredientSaveCount(recipe.ingredients().get(i)));
                slotPositions.add(Pair.of(x, y)); // Store the slot position
            }
        }

        // Check if there is an output item
        if (recipe.result() != null) {
            int outputSlotSize = 18;
            int outputX = (80 - outputSlotSize) / 2; // Center the output slot

            // Output slot
            builder.addSlot(RecipeIngredientRole.OUTPUT, outputX, 50)
                    .addItemStack(recipe.result());
            slotPositions.add(Pair.of(outputX, 50)); // Store the output slot position
        }


        // Check if there is a fluid input
        if (recipe.fluidIngredient().getFluid() != Fluids.EMPTY) {
            builder.addSlot(RecipeIngredientRole.INPUT, 0, 64)
                    .addFluidStack(recipe.fluidIngredient().getFluid(), recipe.fluidIngredient().getAmount());
            slotPositions.add(Pair.of(0, 64)); // Store the fluid input slot position
        }

        // Check if there is a fluid output
        if (recipe.fluidResult().getFluid() != Fluids.EMPTY) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 0, 96)
                    .addFluidStack(recipe.fluidResult().getFluid(), recipe.fluidResult().getAmount());
            slotPositions.add(Pair.of(0, 96)); // Store the fluid output slot position
        }

    }

    @Override
    public void draw(MixingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // Iterate through stored slot positions and render the slot textures
        for (Pair<Integer, Integer> slotPos : slotPositions) {
            int x = slotPos.getFirst();
            int y = slotPos.getSecond();

            // Adjust for 1 pixel up and 1 pixel left
            guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, x - 1, y - 1, 18, 18);
        }

        guiGraphics.blitSprite(DOWN_ARROW_SPRITE, 32, 22, 15, 23); // Adjust position as needed

    }}
