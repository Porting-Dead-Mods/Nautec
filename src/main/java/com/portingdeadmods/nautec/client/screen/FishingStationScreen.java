package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTMachineScreen;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.FishingStationBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class FishingStationScreen extends NTMachineScreen<FishingStationBlockEntity> {
    public static final ResourceLocation TEXTURE = Nautec.rl("textures/gui/fishing_station.png");

    public FishingStationScreen(NTMachineMenu<FishingStationBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}
