package com.portingdeadmods.nautec.client.screen;

import com.portingdeadmods.nautec.NTConfig;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.screen.NTAbstractContainerScreen;
import com.portingdeadmods.nautec.api.client.screen.NTMachineScreen;
import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.BacterialAnalyzerBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.MutatorBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class MutatorScreen extends NTMachineScreen<MutatorBlockEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "textures/gui/mutator.png");
    public static final ResourceLocation PROGRESS_ARROW = Nautec.rl("container/mutator/progress_arrow");

    public MutatorScreen(NTMachineMenu<MutatorBlockEntity> menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, delta, mouseX, mouseY);
        int i = this.leftPos;
        int j = this.topPos;

        int progress = menu.blockEntity.getProgress();

        int j1 = Mth.ceil(((float) progress / NTConfig.mutatorCraftingSpeed) * 62f);

        guiGraphics.blitSprite(PROGRESS_ARROW, 62, 14, 0, 0, i + 56, j + 36, j1, 14);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }
}
