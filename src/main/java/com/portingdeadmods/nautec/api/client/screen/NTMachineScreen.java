package com.portingdeadmods.nautec.api.client.screen;

import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.api.menu.slots.SlotBacteriaStorage;
import com.portingdeadmods.nautec.api.menu.slots.SlotFluidHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public abstract class NTMachineScreen<T extends ContainerBlockEntity> extends AbstractContainerScreen<NTMachineMenu<T>> {
    private SlotFluidHandler hoveredFluidHandlerSlot;
    private SlotBacteriaStorage hoveredBacteriaStorageSlot;

    private final NumberFormat nf = NumberFormat.getIntegerInstance();

    public NTMachineScreen(NTMachineMenu<T> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.titleLabelY = 4;
        this.imageHeight = 174;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics, pMouseX, pMouseX, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        hoverFluidSlot(pMouseX, pMouseY);

        hoverBacteriaSlot(pMouseX, pMouseY);

        Font font = minecraft.font;
        if (this.hoveredBacteriaStorageSlot != null) {
            SimpleCollapsedStats
            pGuiGraphics.renderComponentTooltip(font, List.of(Component.literal("Bacteria Storage")), pMouseX, pMouseY);
        }

        if (this.hoveredFluidHandlerSlot != null) {
            FluidStack fluid = this.hoveredFluidHandlerSlot.getFluidStack();
            pGuiGraphics.renderComponentTooltip(font, List.of(
                    fluid.getHoverName(),
                    Component.literal("%s / %s mb".formatted(
                            nf.format(fluid.getAmount()),
                            nf.format(this.hoveredFluidHandlerSlot.getFluidCapacity()))
                    ).withStyle(ChatFormatting.GRAY)
            ), pMouseX, pMouseY);
        }
    }

    private void hoverFluidSlot(int pMouseX, int pMouseY) {
        for (SlotFluidHandler fSlot : menu.getFluidTankSlots()) {
            if (isHovering(fSlot.getX(), fSlot.getY(), fSlot.getWidth(), fSlot.getHeight(), pMouseX, pMouseY)) {
                this.hoveredFluidHandlerSlot = fSlot;
                return;
            }
        }
        this.hoveredFluidHandlerSlot = null;
    }

    private void hoverBacteriaSlot(int pMouseX, int pMouseY) {
        for (SlotBacteriaStorage bSlot : menu.getBacteriaStorageSlots()) {
            if (isHovering(bSlot.getX(), bSlot.getY(), bSlot.getWidth(), bSlot.getHeight(), pMouseX, pMouseY)) {
                this.hoveredBacteriaStorageSlot = bSlot;
                return;
            }
        }
        this.hoveredBacteriaStorageSlot = null;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        guiGraphics.blit(getBackgroundTexture(), leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    public abstract @NotNull ResourceLocation getBackgroundTexture();

    public SlotFluidHandler getHoveredFluidHandlerSlot() {
        return hoveredFluidHandlerSlot;
    }

    public SlotBacteriaStorage getHoveredBacteriaStorageSlot() {
        return hoveredBacteriaStorageSlot;
    }
}
