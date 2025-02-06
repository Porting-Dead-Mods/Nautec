package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTAbstractContainerScreen;
import com.portingdeadmods.nautec.api.client.screen.NTMachineScreen;
import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.IncubatorBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class IncubatorScreen extends NTMachineScreen<IncubatorBlockEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/incubator.png");
    public static final ResourceLocation PROGRESS_ARROW = Nautec.rl("container/incubator/progress_arrow");

    public IncubatorScreen(NTMachineMenu<IncubatorBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelY = 4;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, delta, mouseX, mouseY);
        int i = this.leftPos;
        int j = this.topPos;

        int progress = menu.blockEntity.getProgress();

        int j1 = (int) Math.ceil(((float) progress / IncubatorBlockEntity.MAX_PROGRESS) * 29f);

        guiGraphics.blitSprite(PROGRESS_ARROW, 46, 29, 0, 29 - j1, i + 65, j + 47 - j1, 46, j1);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}
