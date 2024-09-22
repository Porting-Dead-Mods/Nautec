package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTAbstractContainerScreen;
import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class AugmentationStationScreen extends Screen {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/augment_station.png");
    private final int imageWidth;
    private final int imageHeight;
    private final Player player;
    private final BlockEntity blockEntity;

    public AugmentationStationScreen(AugmentationStationBlockEntity blockEntity, Player player, Component title) {
        super(title);
        this.imageWidth = 176;
        this.imageHeight = 113;
        this.player = player;
        this.blockEntity = blockEntity;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.renderTooltip(this.font, Component.literal(NTItems.DOLPHIN_FIN.getRegisteredName()), x, y);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderTransparentBackground(guiGraphics);
        renderBg(guiGraphics, partialTick, mouseX, mouseY);
    }

    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        guiGraphics.renderFakeItem(NTItems.DOLPHIN_FIN.toStack(), x, y);
        addRenderableWidget(Button.builder(Component.literal("Apply"), btn -> {}).bounds(x + imageWidth / 2 - 26, y + imageHeight - 20, 50, 15).build());
        int xOffset = 60;
        int yOffset = 20;
        renderEntityInInventoryFollowsMouse(guiGraphics, x + xOffset, y + yOffset, x + xOffset + 51, y + 72 + yOffset, 30, 0.0625f, mouseX, mouseY, this.player);
    }

    public static void renderEntityInInventoryFollowsMouse(
            GuiGraphics guiGraphics,
            int x1,
            int y1,
            int x2,
            int y2,
            int scale,
            float yOffset,
            float mouseX,
            float mouseY,
            LivingEntity entity
    ) {
        float f = (float)(x1 + x2) / 2.0F;
        float f1 = (float)(y1 + y2) / 2.0F;
        float f2 = (float)Math.atan((double)((f - mouseX) / 40f));
        float f3 = (float)Math.atan((double)((f1 - mouseY) /40f));
        // Forge: Allow passing in direct angle components instead of mouse position
        InventoryScreen.renderEntityInInventoryFollowsAngle(guiGraphics, x1, y1, x2, y2, scale, yOffset, f2, f3, entity);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
