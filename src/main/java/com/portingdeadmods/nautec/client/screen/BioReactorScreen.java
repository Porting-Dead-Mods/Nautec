package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.client.screen.NTMachineScreen;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.api.menu.slots.SlotBacteriaStorage;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.BioReactorBlockEntity;
import com.portingdeadmods.nautec.network.InsertBacteriaSlotPayload;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class BioReactorScreen extends NTMachineScreen<BioReactorBlockEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/bio_reactor.png");
    public static final ResourceLocation PROGRESS_ARROW = Nautec.rl("container/bio_reactor/progress_arrow");
    public static final ResourceLocation BACTERIA = Nautec.rl("item/petri_dish_overlay");

    public BioReactorScreen(NTMachineMenu<BioReactorBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelY = 4;
        this.imageHeight = 174;
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        for (int i = 0; i < 3; i++) {
            int topPos = this.topPos - 4;

            int progress = menu.blockEntity.getProgress(i);

            int progressPercentage = (int) (((float) progress / 100) * 24);
            pGuiGraphics.blitSprite(PROGRESS_ARROW, 24, 10, 0, 0, this.leftPos + 76, topPos + 20 + i * 22, progressPercentage, 10);
            SlotBacteriaStorage slot = menu.getBacteriaStorageSlots().get(i);
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(BACTERIA);
            BacteriaInstance instance = slot.getBacteriaInstance();
            if (!instance.isEmpty()) {
                Bacteria bacteria = BacteriaHelper.getBacteria(Minecraft.getInstance().level.registryAccess(), instance.getBacteria());
                int color = bacteria.stats().color();
                int r = FastColor.ARGB32.red(color);
                int g = FastColor.ARGB32.green(color);
                int b = FastColor.ARGB32.blue(color);
                int a = FastColor.ARGB32.alpha(color);
                pGuiGraphics.blit(this.leftPos + slot.getX() + 1, this.topPos + slot.getY(), 0, 16, 16, sprite, r / 255f, g / 255f, b / 255f, a / 255f);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        ItemStack carried = menu.getCarried();
        SlotBacteriaStorage slot = getHoveredBacteriaStorageSlot();
        if (carried.is(NTItems.PETRI_DISH) && slot != null) {
            Nautec.LOGGER.debug("index: {}", slot.getSlot());
            IBacteriaStorage itemStorage = carried.getCapability(NTCapabilities.BacteriaStorage.ITEM);
            PacketDistributor.sendToServer(new InsertBacteriaSlotPayload(menu.blockEntity.getBlockPos(), slot.getSlot(), itemStorage.getBacteria(0)));
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
