package com.portingdeadmods.nautec.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class DivingSuitOverlay {
    private static final ResourceLocation OXYGEN_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen");
    private static final ResourceLocation OXYGEN_BURSTING_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen_bursting");
    private static final ResourceLocation OXYGEN_EMPTY_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen_empty");

    private static int burstingTicks = 0;

    private static boolean isWearingFullDivingSuit(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(NTItems.DIVING_HELMET) &&
                player.getItemBySlot(EquipmentSlot.CHEST).is(NTItems.DIVING_CHESTPLATE) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(NTItems.DIVING_LEGGINGS) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(NTItems.DIVING_BOOTS);
    }

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int rightHeight = 59;
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int oxygenLevels = NTDataComponentsUtils.getOxygenLevels(player.getItemBySlot(EquipmentSlot.CHEST));
        if (!player.isUnderWater() || !isWearingFullDivingSuit(player) || oxygenLevels <= 0) {
            return;
        }

        int i1 = guiGraphics.guiWidth() / 2 + 91;

        int i3 = 600;
        int j3 = Math.min(oxygenLevels, i3);
        if (player.isEyeInFluid(FluidTags.WATER) || j3 < i3) {
            int j2 = guiGraphics.guiHeight() - rightHeight;
            int l3 = Mth.ceil((double)(j3 - 2) * 10.0 / (double)i3);
            int i4 = Mth.ceil((double)j3 * 10.0 / (double)i3) - l3;
            RenderSystem.enableBlend();

            for (int j4 = 0; j4 < l3 + i4; j4++) {
                if (j4 < l3) {
                    guiGraphics.blitSprite(OXYGEN_SPRITE, i1 - j4 * 8 - 9, j2, 9, 9);
                } else {
                    guiGraphics.blitSprite(OXYGEN_BURSTING_SPRITE, i1 - j4 * 8 - 9, j2, 9, 9);
                }
            }

            RenderSystem.disableBlend();
            rightHeight += 10;
        }

    }
}
