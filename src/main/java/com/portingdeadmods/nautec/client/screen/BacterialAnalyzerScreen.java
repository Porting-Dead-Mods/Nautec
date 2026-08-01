package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTAbstractContainerScreen;
import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.BacterialAnalyzerBlockEntity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BacterialAnalyzerScreen extends NTAbstractContainerScreen<BacterialAnalyzerBlockEntity> {
    public static final Identifier TEXTURE = Nautec.rl("textures/gui/bacterial_analyzer.png");
    public static final Identifier PROGRESS_ARROW = Nautec.rl("container/bacterial_analyzer/progress_arrow");

    public BacterialAnalyzerScreen(NTAbstractContainerMenu<BacterialAnalyzerBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 174);
        this.titleLabelY = 4;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        int i = this.leftPos;
        int j = this.topPos - 4;

        int progress = menu.blockEntity.getProgress();

        int j1 = Mth.ceil(((float) progress / BacterialAnalyzerBlockEntity.MAX_PROGRESS) * 24.0F);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_ARROW, 24, 24, 0, 0, i + 76, j + 29, j1, 24);
    }

    @Override
    public @NotNull Identifier getBackgroundTexture() {
        return TEXTURE;
    }
}
