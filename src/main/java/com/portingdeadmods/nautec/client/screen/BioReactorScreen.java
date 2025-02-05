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
import com.portingdeadmods.nautec.network.BacteriaSlotClickedPayload;
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
        }
    }
}
