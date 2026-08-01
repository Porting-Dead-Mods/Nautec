package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
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
    protected void drawPanel(GuiGraphicsExtractor guiGraphics, int entryRight, int relativeY, int mouseX, int mouseY) {
        for (int i = 0; i < this.augmentSlots.size(); i++) {
            AugmentSlot augmentSlot = this.augmentSlots.get(i);
            int x = left + 2;
            int y = relativeY - 2 + i * client.font.lineHeight;
            Identifier loc = NTRegistries.AUGMENT_SLOT.getKey(augmentSlot);
            guiGraphics.text(client.font, Component.translatable("augment_slot." + loc.getNamespace() + "." + loc.getPath()),
                    x, y, ARGB.color(255, 255, 255));
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
    protected boolean clickPanel(double mouseX, double mouseY, MouseButtonEvent event) {
        int augmentIndex = (int) (mouseY / client.font.lineHeight);
        if (augmentIndex >= 0 && augmentIndex < this.augmentSlots.size()) {
            this.selectedSlot = this.augmentSlots.get(augmentIndex);
            return true;
        }
        return super.clickPanel(mouseX, mouseY, event);
    }
}
