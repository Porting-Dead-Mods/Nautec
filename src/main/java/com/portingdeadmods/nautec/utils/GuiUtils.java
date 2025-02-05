package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.menu.slots.SlotBacteriaStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.InventoryMenu;

public final class GuiUtils {
    public static final ResourceLocation BACTERIA = Nautec.rl("item/petri_dish_overlay");

    public static void renderBacteria(GuiGraphics guiGraphics, BacteriaInstance instance, int x, int y) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(BACTERIA);
        if (!instance.isEmpty()) {
            Bacteria bacteria = BacteriaHelper.getBacteria(Minecraft.getInstance().level.registryAccess(), instance.getBacteria());
            int color = bacteria.stats().color();
            int r = FastColor.ARGB32.red(color);
            int g = FastColor.ARGB32.green(color);
            int b = FastColor.ARGB32.blue(color);
            int a = FastColor.ARGB32.alpha(color);
            guiGraphics.blit(x + 1, y, 0, 16, 16, sprite, r / 255f, g / 255f, b / 255f, a / 255f);
        }
    }
}
