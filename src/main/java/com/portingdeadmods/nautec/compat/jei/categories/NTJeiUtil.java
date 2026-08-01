package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class NTJeiUtil {
    private NTJeiUtil() {
    }

    public static IDrawable sprite(Identifier sprite, int width, int height) {
        return new IDrawable() {
            @Override
            public int getWidth() {
                return width;
            }

            @Override
            public int getHeight() {
                return height;
            }

            @Override
            public void draw(GuiGraphicsExtractor guiGraphics, int x, int y) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, width, height);
            }
        };
    }

    public static void blitSprite(GuiGraphicsExtractor guiGraphics, Identifier sprite, int x, int y, int width, int height) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, width, height);
    }

    public static void addIngredientWithCount(IRecipeSlotBuilder slot, IngredientWithCount ingredient) {
        if (ingredient.count() > 1) {
            slot.addItemStacks(ingredient.ingredient().items().map(holder -> new ItemStack(holder, ingredient.count())).toList());
        } else {
            slot.add(ingredient.ingredient());
        }
    }
}
