package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTMachineScreen;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class MixerScreen extends NTMachineScreen<MixerBlockEntity> {
    public static final ResourceLocation TEXTURE = Nautec.rl("textures/gui/mixer.png");

    public MixerScreen(NTMachineMenu<MixerBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelY = 4;
        this.imageHeight = 174;
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}
