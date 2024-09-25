package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugmentSlots;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.portingdeadmods.nautec.client.screen.AugmentationStationScreen.BACKGROUND_TEXTURE;

public class AugmentationViewerScreen extends Screen {

    private final int imageWidth;
    private final int imageHeight;
    private int leftPos;
    private int topPos;
    private final Player player;
    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/augments.png");

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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int scale = 30;

        int x1 = leftPos + 10;
        int y1 = topPos + 40;

        int x2 = x1 + 75;
        int y2 = y1 + 75;


        InventoryScreen.renderEntityInInventoryFollowsMouse(
                guiGraphics, x1, y1, x2, y2, scale, 0.0625F, mouseX, mouseY, player
        );
        Map<AugmentSlot, Augment> augments = AugmentHelper.getAugments(player);
        int y = y1 - 40;
        List<AugmentSlot> emptySlots = new ArrayList<>();
        List<AugmentSlot> fullSlots = new ArrayList<>();

        for (AugmentSlot augmentSlot : augments.keySet()) {
            Augment augment = augments.get(augmentSlot);
            if (augment == null) {
                // TODO : Switch from the getAugments because that doesn't contain the null augments in empty slots
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

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    // TODO: Display a short description for the augments too, aswell as a proper name and not the registry name for the augment

    public void displayAugment(GuiGraphics graphics, AugmentSlot slot, Augment aug, int x, int y) {
        graphics.drawString(this.font, Component.translatable("augment_slot.nautec." + slot.getName()).append(Component.literal(":")), x, y, 0);
        graphics.drawString(this.font, aug == null ? Component.literal("    No Augment in slot") : Component.literal("    ").append(Component.translatable("augment." + aug.getAugmentType().toString())), x, y + 10, 0);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}
