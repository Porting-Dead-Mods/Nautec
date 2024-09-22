package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTAbstractContainerScreen;
import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class AugmentationStationScreen extends NTAbstractContainerScreen<AugmentationStationBlockEntity> {
    public AugmentationStationScreen(NTAbstractContainerMenu<AugmentationStationBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/augmentation_station.png");
    }
}
