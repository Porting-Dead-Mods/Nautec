package com.portingdeadmods.nautec.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class DivingSuitOverlay {
    private static final ResourceLocation OXYGEN_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen");
    private static final ResourceLocation OXYGEN_BURSTING_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen_bursting");
    private static final ResourceLocation OXYGEN_EMPTY_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen_empty");

    private static int burstingTicks = 0;

    public static final LayeredDraw.Layer HUD = ((guiGraphics, deltaTracker) -> {
        Player player = Minecraft.getInstance().player;
        if (player == null || !player.isUnderWater() || !isWearingFullDivingSuit(player) || NTDataComponentsUtils.getOxygenLevels(player.getItemBySlot(EquipmentSlot.CHEST)) <= 0) {
            return;
        }

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        int rightHeight = 39;

        // Oxygen bar values
        ItemStack chestPiece = player.getItemBySlot(EquipmentSlot.CHEST);
        int maxOxygen = 300;
        int currentOxygen = NTDataComponentsUtils.getOxygenLevels(chestPiece);

        // Bar position
        int xStart = screenWidth / 2 + 91;
        int yStart = screenHeight - rightHeight - 20;

        int barLength = Mth.ceil((double) currentOxygen * 10.0 / (double) maxOxygen);
        RenderSystem.enableBlend();

        // Draw oxygen bar
        for (int i = 0; i < 10; i++) {
            if (i < barLength) {
                guiGraphics.blitSprite(OXYGEN_SPRITE, xStart - i * 8 - 9, yStart, 9, 9); // Oxygen-filled sprite
            } else {
                // Only render the bursting sprite for the first 5 ticks
                if (burstingTicks < 5) {
                    guiGraphics.blitSprite(OXYGEN_BURSTING_SPRITE, xStart - i * 8 - 9, yStart, 9, 9); // Bursting oxygen sprite
                } else {
                    guiGraphics.blitSprite(OXYGEN_EMPTY_SPRITE, xStart - i * 8 - 9, yStart, 9, 9); // After 5 ticks, show the empty oxygen sprite
                }
            }
        }

        // Increment bursting ticks and reset after 5
        burstingTicks++;
        if (burstingTicks >= 5) {
            burstingTicks = 0;
        }

        RenderSystem.disableBlend();
    });

    private static boolean isWearingFullDivingSuit(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(NTItems.DIVING_HELMET) &&
                player.getItemBySlot(EquipmentSlot.CHEST).is(NTItems.DIVING_CHESTPLATE) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(NTItems.DIVING_LEGGINGS) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(NTItems.DIVING_BOOTS);
    }
}
