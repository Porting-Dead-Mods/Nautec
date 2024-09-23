package com.portingdeadmods.nautec.client.screen;

import com.mojang.blaze3d.vertex.Tesselator;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;

import java.util.List;

public class AugmentationStationDataPanel extends ScrollPanel {
    private final List<AugmentSlot> augmentSlots;
    private final Minecraft client;

    private AugmentSlot selectedSlot;

    public AugmentationStationDataPanel(Minecraft client, int width, int height, int top, int left) {
        super(client, width, height, top, left);
        this.client = client;
        this.augmentSlots = NTRegistries.AUGMENT_SLOT.stream().toList();
    }

    @Override
    protected int getContentHeight() {
        return this.augmentSlots.size() * client.font.lineHeight;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        for (int i = 0; i < this.augmentSlots.size(); i++) {
            AugmentSlot augmentSlot = this.augmentSlots.get(i);
            int x = left + 6;
            int y = relativeY + i * client.font.lineHeight;
            guiGraphics.drawString(client.font, Component.literal(augmentSlot.getName()),
                    x, y, FastColor.ARGB32.color(255, 255, 255));
            if (augmentSlot == selectedSlot) {
                Nautec.LOGGER.debug("selected: {}", augmentSlot);
            }
        }
    }

    public AugmentSlot getSelectedSlot() {
        return selectedSlot;
    }

    public int getSelectedSlotIndex() {
        return this.augmentSlots.indexOf(selectedSlot);
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    protected boolean clickPanel(double mouseX, double mouseY, int button) {
        int augmentIndex = (int) (mouseY / client.font.lineHeight);
        if (augmentIndex >= 0) {
            this.selectedSlot = this.augmentSlots.get(augmentIndex);
            return true;
        }
        return super.clickPanel(mouseX, mouseY, button);
    }
}
