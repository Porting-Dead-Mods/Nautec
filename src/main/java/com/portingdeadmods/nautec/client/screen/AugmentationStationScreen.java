package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.content.recipes.AugmentationRecipe;
import com.portingdeadmods.nautec.network.StartAugmentationPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;

public class AugmentationStationScreen extends Screen {
    public static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/augment_station.png");
    public static final ResourceLocation SELECTED_SLOT_IN = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/selected_slot_in.png");
    public static final ResourceLocation SELECTED_SLOT_OUT = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/selected_slot_out.png");

    private final int imageWidth;
    private final int imageHeight;
    private final Player player;
    private final AugmentationStationBlockEntity blockEntity;
    private AugmentationStationDataPanel dataPanel;

    private final AugmentationRecipe recipe;

    public AugmentationStationScreen(AugmentationStationBlockEntity blockEntity, Player player, Component title) {
        super(title);
        this.imageWidth = 202;
        this.imageHeight = 160;
        this.player = player;
        this.blockEntity = blockEntity;
        this.recipe = blockEntity.getRecipe().orElse(null);
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int width = (x + imageWidth - 4) - (x + 4);
        int height = (y + imageHeight - 6) - (y + 16);

        this.dataPanel = new AugmentationStationDataPanel(Minecraft.getInstance(), width / 3 - 10, height - 50, y + 18, x + imageWidth - 58);
        addRenderableWidget(this.dataPanel);
        this.dataPanel.setAugmentSlots(recipe != null ? recipe.resultAugment().getAugmentSlots() : NTRegistries.AUGMENT_SLOT.stream().toList());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int augmentX = x + 24;
        int augmentY = y + 48;

        if (mouseX > augmentX && mouseX < augmentX + 16 && mouseY > augmentY && mouseY < augmentY + 16) {
            if (recipe != null){
                Item augmentItem = recipe.augmentItem();
                if (augmentItem != Items.AIR) {
                    guiGraphics.renderTooltip(this.font, augmentItem.getName(augmentItem.getDefaultInstance()), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(guiGraphics);
        renderBg(guiGraphics, partialTick, mouseX, mouseY);
    }

    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(BACKGROUND_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        int augmentX = x + 24;
        int augmentY = y + 48;

        if (recipe != null) {
            Item augmentItem = recipe.augmentItem();

            if (augmentItem != Items.AIR) {
                guiGraphics.renderFakeItem(augmentItem.getDefaultInstance(), augmentX, augmentY);
                guiGraphics.blit(SELECTED_SLOT_IN, augmentX - 4, augmentY - 4, 0, 0, 24, 24, 24, 24);
            } else {
                guiGraphics.blit(SELECTED_SLOT_OUT, augmentX - 4, augmentY - 4, 0, 0, 24, 24, 24, 24);
            }
        }

        addRenderableWidget(Button.builder(Component.literal("Apply"), btn -> {
            blockEntity.startAugmentation(player, dataPanel.getSelectedSlot());
            PacketDistributor.sendToServer(new StartAugmentationPayload(blockEntity.getBlockPos(), dataPanel.getSelectedSlot(), player.getUUID()));
            Minecraft.getInstance().setScreen(null);
        }).bounds(x + imageWidth / 2 - 40, y + imageHeight - 55, 50, 15).build());

        int xOffset = 60;
        int yOffset = 20;
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x + xOffset, y + yOffset, x + xOffset + 51, y + 72 + yOffset, 30, 0.0625f, mouseX, mouseY, this.player);

        renderRect(guiGraphics);

        MutableComponent text = Component.literal("Augmentation Station");
        int textWidth = minecraft.font.width(text);
        guiGraphics.drawString(minecraft.font, text, x + (imageWidth / 2) - (textWidth / 2) - 14, y + 2, 4210752, false);
    }

    private void renderRect(GuiGraphics guiGraphics) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int width = (x + imageWidth - 4) - (x + 4);
        int height = (y + imageHeight - 6) - (y + 16);
        int minX = x + imageWidth - 58;
        int lineHeight = minecraft.font.lineHeight;
        int slotIndex = dataPanel.getSelectedSlotIndex();
        int minY = y + 16 + slotIndex * lineHeight;
        if (slotIndex != -1) {
            guiGraphics.fill(minX, minY + 5, minX + width / 3 - 10, minY + lineHeight + 4, FastColor.ARGB32.color(150, 150, 150));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
