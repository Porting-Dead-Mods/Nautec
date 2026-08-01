package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public final class GuiUtils {
    public static final Identifier BACTERIA = Nautec.rl("item/petri_dish_overlay");

    public static void renderBacteria(GuiGraphicsExtractor guiGraphics, BacteriaInstance instance, int x, int y) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.BLOCKS).getSprite(BACTERIA);
        if (!instance.isEmpty()) {
            Bacteria bacteria = BacteriaHelper.getBacteria(Minecraft.getInstance().level.registryAccess(), instance.getBacteria());
            int color = bacteria.stats().color();
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x + 1, y, 16, 16, ARGB.color(ARGB.alpha(color), ARGB.red(color), ARGB.green(color), ARGB.blue(color)));
        }
    }

    public static boolean isHovering(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int mouseX, int mouseY) {
        return guiGraphics.containsPointInScissor(mouseX, mouseY)
                && mouseX > x
                && mouseY > y
                && mouseX < x + width
                && mouseY < y + height;
    }
}
