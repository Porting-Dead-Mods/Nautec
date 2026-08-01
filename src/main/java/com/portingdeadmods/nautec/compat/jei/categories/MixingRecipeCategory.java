package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class MixingRecipeCategory extends AbstractRecipeCategory<MixingRecipe> {
    static final Identifier SINGLE_SLOT_SPRITE = Identifier.fromNamespaceAndPath(Nautec.MODID, "container/furnace/empty_slot");
    static final Identifier DOWN_ARROW_SPRITE = Identifier.fromNamespaceAndPath(Nautec.MODID, "container/furnace/down_arrow");
    public static final Identifier UID = Nautec.rl(MixingRecipe.NAME);
    public static final IRecipeType<MixingRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, MixingRecipe.class);

    public MixingRecipeCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.mixing"),
                helper.createDrawableItemStack(new ItemStack(NTBlocks.MIXER.get())),
                106,
                66);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MixingRecipe recipe, IFocusGroup focuses) {
        int maxSlots = 4;
        int slotSize = 18;
        int gap = 2;

        if (!recipe.ingredients().isEmpty()) {
            int totalSlotWidth = (Math.min(recipe.ingredients().size(), maxSlots) * slotSize)
                    + ((Math.min(recipe.ingredients().size(), maxSlots) - 1) * gap);

            int startX = (80 - totalSlotWidth) / 2;

            for (int i = 0; i < recipe.ingredients().size(); i++) {
                int x = startX + i * (slotSize + gap);
                NTJeiUtil.addIngredientWithCount(builder.addSlot(RecipeIngredientRole.INPUT, x, 0)
                        .setBackground(NTJeiUtil.sprite(SINGLE_SLOT_SPRITE, 18, 18), -1, -1), recipe.ingredients().get(i));
            }
        }

        if (recipe.result() != null) {
            int outputSlotSize = 18;
            int outputX = (80 - outputSlotSize) / 2;

            builder.addSlot(RecipeIngredientRole.OUTPUT, outputX, 50)
                    .setBackground(NTJeiUtil.sprite(SINGLE_SLOT_SPRITE, 18, 18), -1, -1)
                    .add(recipe.result());
        }

        if (recipe.fluidIngredient().getFluid() != Fluids.EMPTY) {
            builder.addSlot(RecipeIngredientRole.INPUT, 88, 0)
                    .add(recipe.fluidIngredient().getFluid(), recipe.fluidIngredient().getAmount())
                    .setFluidRenderer(recipe.fluidIngredient().getAmount(), true, 16, 16);
        }

        if (recipe.fluidResult().getFluid() != Fluids.EMPTY) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 48)
                    .add(recipe.fluidResult().getFluid(), recipe.fluidResult().getAmount())
                    .setFluidRenderer(recipe.fluidResult().getAmount(), true, 16, 16);
        }
    }

    @Override
    public void draw(@NotNull MixingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        NTJeiUtil.blitSprite(guiGraphics, SINGLE_SLOT_SPRITE, 87, -1, 18, 18);
        NTJeiUtil.blitSprite(guiGraphics, SINGLE_SLOT_SPRITE, 87, 47, 18, 18);

        NTJeiUtil.blitSprite(guiGraphics, DOWN_ARROW_SPRITE, 32, 22, 15, 23);
        NTJeiUtil.blitSprite(guiGraphics, DOWN_ARROW_SPRITE, 88, 22, 15, 23);
    }
}
