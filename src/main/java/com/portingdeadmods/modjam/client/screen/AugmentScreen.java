package com.portingdeadmods.modjam.client.screen;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.menus.AugmentMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.util.FastColor;

public class AugmentScreen extends AbstractContainerScreen<AugmentMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "textures/gui/augments.png");

    private int imageWidth;
    private int imageHeight;

    public AugmentScreen(AugmentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 4;
        this.titleLabelY = 20;
        this.inventoryLabelY = -500;
        this.imageWidth = 190;
        this.imageHeight = 136;
    }
}
