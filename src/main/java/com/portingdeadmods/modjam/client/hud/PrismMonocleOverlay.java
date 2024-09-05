package com.portingdeadmods.modjam.client.hud;

import com.portingdeadmods.modjam.api.blocks.DisplayBlock;
import com.portingdeadmods.modjam.content.items.PrismMonocleItem;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public final class PrismMonocleOverlay {
    public static final LayeredDraw.Layer HUD = (guiGraphics, delta) -> {
        Minecraft mc = Minecraft.getInstance();
        int lineOffset = 0;
        int x = guiGraphics.guiWidth() / 2;
        int y = guiGraphics.guiHeight() / 2;
        Level level = mc.level;
        Player player = mc.player;
        ItemStack item = player.getItemBySlot(EquipmentSlot.HEAD);

        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(MJItems.PRISM_MONOCLE.get()));

        if ((item.getItem() instanceof PrismMonocleItem || slotResult.isPresent()) && mc.hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (level.getBlockState(blockPos).getBlock() instanceof DisplayBlock displayBlock && displayBlock.display(level, blockPos, player)) {
                for (Component component : displayBlock.displayText(level, blockPos, player)) {
                    guiGraphics.drawCenteredString(mc.font, component, x, y + lineOffset, 256);
                    lineOffset += mc.font.lineHeight + 3;
                }
            }
        }
    };
}