package com.portingdeadmods.modjam.client.screen;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.client.screen.MJAbstractContainerScreen;
import com.portingdeadmods.modjam.api.menu.MJAbstractContainerMenu;
import com.portingdeadmods.modjam.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class AugmentationStationExtensionScreen extends MJAbstractContainerScreen<AugmentationStationExtensionBlockEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "textures/gui/augment_station_extension.png");

    public AugmentationStationExtensionScreen(MJAbstractContainerMenu<AugmentationStationExtensionBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelY = 4;
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}
