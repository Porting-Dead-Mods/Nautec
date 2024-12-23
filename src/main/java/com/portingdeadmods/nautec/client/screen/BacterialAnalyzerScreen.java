package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTAbstractContainerScreen;
import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.BacterialAnalyzerBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BacterialAnalyzerScreen extends NTAbstractContainerScreen<BacterialAnalyzerBlockEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/bacterial_analyzer.png");
    public static final ResourceLocation PROGRESS_ARROW = Nautec.rl("container/bacterial_analyzer/progress_arrow");

    public BacterialAnalyzerScreen(NTAbstractContainerMenu<BacterialAnalyzerBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelY = 4;
        this.imageHeight = 174;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, delta, mouseX, mouseY);
        int i = this.leftPos;
        int j = this.topPos - 4;

        int progress = menu.blockEntity.getProgress();

        int j1 = Mth.ceil(((float) progress / BacterialAnalyzerBlockEntity.MAX_PROGRESS) * 24.0F);
        guiGraphics.blitSprite(PROGRESS_ARROW, 24, 24, 0, 0, i + 76, j + 29, j1, 24);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}
