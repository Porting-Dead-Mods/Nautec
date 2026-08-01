package com.portingdeadmods.nautec.api.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.api.menu.slots.SlotBacteriaStorage;
import com.portingdeadmods.nautec.api.menu.slots.SlotFluidHandler;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.network.BacteriaSlotClickedPayload;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public abstract class NTMachineScreen<T extends ContainerBlockEntity> extends AbstractContainerScreen<NTMachineMenu<T>> {
    private static final Identifier BACTERIA_OVERLAY_TEXTURE = Nautec.rl("textures/item/petri_dish_overlay.png");

    private SlotFluidHandler hoveredFluidHandlerSlot;
    private SlotBacteriaStorage hoveredBacteriaStorageSlot;

    private final NumberFormat nf = NumberFormat.getIntegerInstance();

    public NTMachineScreen(NTMachineMenu<T> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 174);

        this.titleLabelY = 4;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), leftPos, topPos, 0F, 0F, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        hoverFluidSlot(mouseX, mouseY);
        hoverBacteriaSlot(mouseX, mouseY);

        Font font = minecraft.font;
        SlotBacteriaStorage slot = this.hoveredBacteriaStorageSlot;
        if (slot != null) {
            BacteriaInstance bacteria = slot.getBacteriaStorage().getBacteria(slot.getSlot());
            if (!bacteria.isEmpty()) {
                List<Component> tooltip = bacteria.getTooltip();
                guiGraphics.setComponentTooltipForNextFrame(font, tooltip, mouseX, mouseY);
            }
            int color = ARGB.color(20, 30, 30, 30);
            guiGraphics.fillGradient(
                    this.leftPos + slot.getX() + 2,
                    this.topPos + slot.getY() + 2,
                    this.leftPos + slot.getX() + slot.getWidth() - 2,
                    this.topPos + slot.getY() + slot.getHeight() - 2, color,
                    color
            );
        }

        if (this.hoveredFluidHandlerSlot != null) {
            FluidStack fluid = this.hoveredFluidHandlerSlot.getFluidStack();
            guiGraphics.setComponentTooltipForNextFrame(font, List.of(
                    fluid.getHoverName(),
                    Component.literal("%s / %s mb".formatted(
                            nf.format(fluid.getAmount()),
                            nf.format(this.hoveredFluidHandlerSlot.getFluidCapacity()))
                    ).withStyle(ChatFormatting.GRAY)
            ), mouseX, mouseY);
        }

        for (SlotBacteriaStorage bSlot : this.menu.getBacteriaStorageSlots()) {
            renderBacteria(guiGraphics, bSlot.getBacteriaInstance(), this.leftPos + bSlot.getX(), this.topPos + bSlot.getY());
        }

        for (SlotFluidHandler fSlot : this.menu.getFluidTankSlots()) {
            fSlot.getRenderer().render(guiGraphics, this.leftPos + fSlot.getX(), this.topPos + fSlot.getY(), fSlot.getFluidStack());
        }
    }

    private void renderBacteria(GuiGraphicsExtractor guiGraphics, BacteriaInstance instance, int x, int y) {
        if (!instance.isEmpty()) {
            Bacteria bacteria = BacteriaHelper.getBacteria(Minecraft.getInstance().level.registryAccess(), instance.getBacteria());
            int color = bacteria.stats().color();
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACTERIA_OVERLAY_TEXTURE, x + 1, y, 0F, 0F, 16, 16, 16, 16, color);
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

    public abstract @NotNull Identifier getBackgroundTexture();

    public SlotFluidHandler getHoveredFluidHandlerSlot() {
        return hoveredFluidHandlerSlot;
    }

    public SlotBacteriaStorage getHoveredBacteriaStorageSlot() {
        return hoveredBacteriaStorageSlot;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        ItemStack carried = menu.getCarried();
        SlotBacteriaStorage slot = getHoveredBacteriaStorageSlot();
        if (carried.is(NTItems.PETRI_DISH) && slot != null) {
            IBacteriaStorage itemStorage = carried.getCapability(NTCapabilities.BacteriaStorage.ITEM);
            ClientPacketDistributor.sendToServer(new BacteriaSlotClickedPayload(menu.blockEntity.getBlockPos(), slot.getSlot(), itemStorage.getBacteria(0)));
        }
        return super.mouseClicked(event, doubleClick);
    }
}
