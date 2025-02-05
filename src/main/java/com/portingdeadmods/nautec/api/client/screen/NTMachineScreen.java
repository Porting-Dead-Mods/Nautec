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
import com.portingdeadmods.nautec.utils.GuiUtils;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;
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

        for (SlotBacteriaStorage bSlot : this.menu.getBacteriaStorageSlots()) {
            GuiUtils.renderBacteria(pGuiGraphics, bSlot.getBacteriaInstance(), this.leftPos + bSlot.getX(), this.topPos + bSlot.getY());
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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        ItemStack carried = menu.getCarried();
        SlotBacteriaStorage slot = getHoveredBacteriaStorageSlot();
        if (carried.is(NTItems.PETRI_DISH) && slot != null) {
            Nautec.LOGGER.debug("index: {}", slot.getSlot());
            IBacteriaStorage itemStorage = carried.getCapability(NTCapabilities.BacteriaStorage.ITEM);
            PacketDistributor.sendToServer(new BacteriaSlotClickedPayload(menu.blockEntity.getBlockPos(), slot.getSlot(), itemStorage.getBacteria(0)));
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
