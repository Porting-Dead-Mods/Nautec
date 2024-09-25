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

    private final Player player;

    public AugmentationViewerScreen(Component title, Player player) {
        super(title);
        this.player = player;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int scale = 30;

        int x1 = 115;
        int y1 = 75;

        int x2 = 165;
        int y2 = 175;


        InventoryScreen.renderEntityInInventoryFollowsMouse(
                guiGraphics, x1, y1, x2, y2, scale, 0.0625F, mouseX, mouseY, player
        );
        Map<AugmentSlot, Augment> augments = AugmentHelper.getAugments(player);
        int y = 50;
        List<AugmentSlot> emptySlots = new ArrayList<>();
        List<AugmentSlot> fullSlots = new ArrayList<>();

        for (AugmentSlot augmentSlot : augments.keySet()) {
            Augment augment = augments.get(augmentSlot);
            //Nautec.LOGGER.info(augment.getAugmentType().toString());
            if (augment == null) {
                // TODO : Switch from the getAugments because that doesn't contain the null augments in empty slots
                //Nautec.LOGGER.info("null augment");
                emptySlots.add(augmentSlot);
            } else {
                fullSlots.add(augmentSlot);
            }
        }

        for (AugmentSlot slot : fullSlots) {
            displayAugment(guiGraphics, slot, augments.get(slot), 200, y += 20);
        }
        for (AugmentSlot slot : emptySlots) {
            displayAugment(guiGraphics, slot, augments.get(slot), 200, y += 20);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    // TODO: Display a short description for the augments too, aswell as a proper name and not the registry name for the augment

    public void displayAugment(GuiGraphics graphics, AugmentSlot slot, Augment aug, int x, int y) {
        graphics.drawString(this.font, slot.getName() + ":", x, y, 0);
        graphics.drawString(this.font, aug == null ? "    No Augment in slot" : "    " + aug.getAugmentType().toString(), x, y + 10, 0);
    }

    public void displayAugment(GuiGraphics graphics, Supplier<AugmentSlot> augmSupplier, Map<AugmentSlot, Augment> augments, int x, int y) {
        graphics.drawString(this.font, augmSupplier.get().getName() + ":", x, y, 0);
        graphics.drawString(this.font, augments.get(augmSupplier.get()) == null ? "    No Augment in slot" : "    " + augments.get(augmSupplier.get()).getAugmentType().toString(), x, y + 10, 0);
    }

    public void displayAugmentB(GuiGraphics graphics, Supplier<AugmentSlot> augmSupplier, Map<AugmentSlot, Augment> augments, int x, int y) {
        graphics.drawString(this.font, augmSupplier.get().getName() + ": " + ((augments.get(augmSupplier.get()) == null) ? "None" : augments.get(augmSupplier.get()).getAugmentType().toString()), x, y, 0);
        // graphics.drawString(this.font, augments.get(augmSupplier.get())==null?"    No Augment in slot":"    "+augments.get(augmSupplier.get()).getAugmentType().toString(), x, y+10, 0);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = (width - 256) / 2;
        int y = (height - 135) / 2;
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/augments.png"), x, y, 0, 0, 256, 135, 256, 256);

    }
}
