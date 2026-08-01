package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AugmentationViewerScreen extends Screen {

    private final int imageWidth;
    private final int imageHeight;
    private int leftPos;
    private int topPos;
    private final Player player;
    public static final Identifier BACKGROUND = Nautec.rl("textures/gui/augments.png");

    public AugmentationViewerScreen(Component title, Player player) {
        super(title);
        this.player = player;
        this.imageWidth = 202;
        this.imageHeight = 160;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int scale = 30;

        int x1 = leftPos + 10;
        int y1 = topPos + 40;

        int x2 = x1 + 75;
        int y2 = y1 + 75;


        InventoryScreen.extractEntityInInventoryFollowsMouse(
                guiGraphics, x1, y1, x2, y2, scale, 0.0625F, mouseX, mouseY, player
        );
        Map<AugmentSlot, Augment> augments = AugmentHelper.getAugments(player);
        int y = y1 - 40;
        List<AugmentSlot> emptySlots = new ArrayList<>();
        List<AugmentSlot> fullSlots = new ArrayList<>();

        for (AugmentSlot augmentSlot : augments.keySet()) {
            if (augmentSlot == null) continue;
            Augment augment = augments.get(augmentSlot);
            if (augment == null) {
                emptySlots.add(augmentSlot);
            } else {
                fullSlots.add(augmentSlot);
            }
        }

        for (AugmentSlot slot : fullSlots) {
            displayAugment(guiGraphics, slot, augments.get(slot), x2, y += 20);
        }
        for (AugmentSlot slot : emptySlots) {
            displayAugment(guiGraphics, slot, augments.get(slot), x2, y += 20);
        }

        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
    }

    public void displayAugment(GuiGraphicsExtractor graphics, AugmentSlot slot, Augment aug, int x, int y) {
        graphics.text(this.font, Component.translatable("augment_slot.nautec." + slot.getName()).append(Component.literal(":")), x, y, ARGB.opaque(0));
        graphics.text(this.font, aug == null ? Component.literal("    No Augment in slot") : Component.literal("    ").append(Component.translatable("augment_type." + aug.getAugmentType().toString())), x, y + 10, ARGB.opaque(0));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, leftPos, topPos, 0F, 0F, imageWidth, imageHeight, 256, 256);
    }
}
