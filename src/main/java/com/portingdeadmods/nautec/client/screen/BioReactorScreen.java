package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTAbstractContainerScreen;
import com.portingdeadmods.nautec.api.client.screen.NTMachineScreen;
import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.BioReactorBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BioReactorScreen extends NTMachineScreen<BioReactorBlockEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/bio_reactor.png");

    public BioReactorScreen(NTMachineMenu<BioReactorBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelY = 4;
        this.imageHeight = 174;
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}
