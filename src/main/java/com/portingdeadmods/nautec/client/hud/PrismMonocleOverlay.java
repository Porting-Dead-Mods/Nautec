package com.portingdeadmods.nautec.client.hud;

import com.portingdeadmods.nautec.api.blocks.DisplayBlock;
import com.portingdeadmods.nautec.compat.curio.CurioCompat;
import com.portingdeadmods.nautec.content.items.PrismMonocleItem;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.client.gui.GuiLayer;

public final class PrismMonocleOverlay {
    public static final GuiLayer HUD = (guiGraphics, delta) -> {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        Player player = mc.player;
        if (level == null || player == null) return;

        int lineOffset = 0;
        int x = guiGraphics.guiWidth() / 2;
        int y = guiGraphics.guiHeight() / 2;
        ItemStack item = player.getItemBySlot(EquipmentSlot.HEAD);

        ItemStack slotResult = CurioCompat.getStackInSlot(player, NTItems.PRISM_MONOCLE.get());

        if ((item.getItem() instanceof PrismMonocleItem || !slotResult.isEmpty()) && mc.hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (level.getBlockState(blockPos).getBlock() instanceof DisplayBlock displayBlock && displayBlock.display(level, blockPos, player)) {
                for (Component component : displayBlock.displayText(level, blockPos, player)) {
                    guiGraphics.centeredText(mc.font, component, x, y + lineOffset, ARGB.opaque(256));
                    lineOffset += mc.font.lineHeight + 3;
                }
            }
        }
    };
}
