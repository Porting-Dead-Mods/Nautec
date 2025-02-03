package com.portingdeadmods.nautec.api.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.api.menu.slots.SlotBacteriaStorage;
import com.portingdeadmods.nautec.api.menu.slots.SlotFluidHandler;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
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

        hoverBacteriaSlot(pGuiGraphics, pMouseX, pMouseY);

        Font font = minecraft.font;
        SlotBacteriaStorage slot = this.hoveredBacteriaStorageSlot;
        if (slot != null) {
            BacteriaInstance bacteria = slot.getBacteriaStorage().getBacteria(slot.getSlot());
            if (!bacteria.isEmpty()) {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(Utils.registryTranslation(bacteria.getBacteria()));
                tooltip.addAll(bacteria.getStats().statsTooltip());
                pGuiGraphics.renderComponentTooltip(font, tooltip, pMouseX, pMouseY);
            }
            int color = FastColor.ARGB32.color(20, 30, 30, 30);
            pGuiGraphics.fillGradient(
                    this.leftPos + slot.getX() + 2,
                    this.topPos + slot.getY() + 2,
                    this.leftPos + slot.getX() + slot.getWidth() - 2,
                    this.topPos + slot.getY() + slot.getHeight() - 2, color,
                    color
            );
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

    private void hoverBacteriaSlot(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        for (SlotBacteriaStorage bSlot : menu.getBacteriaStorageSlots()) {
            if (isHovering(bSlot.getX(), bSlot.getY(), bSlot.getWidth(), bSlot.getHeight(), pMouseX, pMouseY)) {
                this.hoveredBacteriaStorageSlot = bSlot;
                Nautec.LOGGER.debug("Slot: {}", bSlot.getSlot());
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

    private boolean isHovering(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y, int width, int height) {
        return (guiGraphics == null ||
                guiGraphics.containsPointInScissor(mouseX, mouseY))
                && mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
