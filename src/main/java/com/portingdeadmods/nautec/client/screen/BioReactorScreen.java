package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTMachineScreen;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.BioReactorBlockEntity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BioReactorScreen extends NTMachineScreen<BioReactorBlockEntity> {
    public static final Identifier TEXTURE = Nautec.rl("textures/gui/bio_reactor.png");
    public static final Identifier PROGRESS_ARROW = Nautec.rl("container/bio_reactor/progress_arrow");

    public BioReactorScreen(NTMachineMenu<BioReactorBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelY = 4;
    }

    @Override
    public @NotNull Identifier getBackgroundTexture() {
        return TEXTURE;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        for (int i = 0; i < 3; i++) {
            int topPos = this.topPos - 4;

            int progress = menu.blockEntity.getProgress(i);

            int progressPercentage = (int) (((float) progress / 100) * 24);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESS_ARROW, 24, 10, 0, 0, this.leftPos + 76, topPos + 20 + i * 22, progressPercentage, 10);
        }
    }
}
