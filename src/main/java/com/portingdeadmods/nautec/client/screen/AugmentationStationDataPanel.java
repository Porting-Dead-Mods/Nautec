package com.portingdeadmods.nautec.client.screen;

import com.mojang.blaze3d.vertex.Tesselator;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class AugmentationStationDataPanel extends ScrollPanel {
    private List<AugmentSlot> augmentSlots;
    private final Minecraft client;

    private AugmentSlot selectedSlot;

    public AugmentationStationDataPanel(Minecraft client, int width, int height, int top, int left) {
        super(client, width, height, top, left);
        this.client = client;
        this.augmentSlots = Collections.emptyList();

        applyScrollLimits();

    }

    private int getMaxScroll() {
        int contentHeight = this.getContentHeight();
        return Math.max(contentHeight - (this.height - this.border), 0);
    }

    private void applyScrollLimits() {
        int max = getMaxScroll();

        if (max < 0) {
            max /= 2;
        }

        if (this.scrollDistance < 0.0F) {
            this.scrollDistance = 0.0F;
        }

        if (this.scrollDistance > max) {
            this.scrollDistance = max;
        }
    }

    @Override
    protected int getContentHeight() {
        return this.augmentSlots.size() * client.font.lineHeight;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        Nautec.LOGGER.debug("relative y: {}", relativeY);
        for (int i = 0; i < this.augmentSlots.size(); i++) {
            AugmentSlot augmentSlot = this.augmentSlots.get(i);
            int x = left + 2;
            int y = relativeY - 2 + i * client.font.lineHeight;
            ResourceLocation loc = NTRegistries.AUGMENT_SLOT.getKey(augmentSlot);
            guiGraphics.drawString(client.font, Component.translatable("augment_slot."+loc.getNamespace()+"."+loc.getPath()),
                    x, y, FastColor.ARGB32.color(255, 255, 255));
        }
    }

    public void setAugmentSlots(List<AugmentSlot> augmentSlots) {
        applyScrollLimits();
        this.augmentSlots = augmentSlots;
    }

    public AugmentSlot getSelectedSlot() {
        return selectedSlot;
    }

    public int getSelectedSlotIndex() {
        return selectedSlot != null ? this.augmentSlots.indexOf(selectedSlot) : -1;
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
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
