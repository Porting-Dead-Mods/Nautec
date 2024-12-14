package com.portingdeadmods.nautec.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.registries.NTFluidTypes;
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
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;


public final class DivingSuitOverlay {
    private static final ResourceLocation OXYGEN_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen");
    private static final ResourceLocation OXYGEN_BURSTING_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen_bursting");
    private static final ResourceLocation OXYGEN_EMPTY_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"hud/oxygen_empty");

    private static boolean isWearingFullDivingSuit(@NotNull Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(NTItems.DIVING_HELMET) &&
                player.getItemBySlot(EquipmentSlot.CHEST).is(NTItems.DIVING_CHESTPLATE) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(NTItems.DIVING_LEGGINGS) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(NTItems.DIVING_BOOTS);
    }

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        int rightOffset = 59;
        int maxOxygen = 600;
        int spriteSize = 9;

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int oxygenLevels = NTDataComponentsUtils.getOxygenLevels(player.getItemBySlot(EquipmentSlot.CHEST));
        if (!player.isUnderWater() || !isWearingFullDivingSuit(player) || oxygenLevels <= 0) {
            return;
        }

        int xBase = guiGraphics.guiWidth() / 2 + 91;
        int yBase = guiGraphics.guiHeight() - rightOffset;
        int visibleOxygen = Math.min(oxygenLevels, maxOxygen);
        int fullBubbles = Mth.ceil((double)(visibleOxygen - 2) * 10.0 / maxOxygen);
        int burstingBubbles = Mth.ceil((double)visibleOxygen * 10.0 / maxOxygen) - fullBubbles;


        if (player.isEyeInFluidType(Fluids.WATER.getFluidType()) || visibleOxygen < maxOxygen) {
            RenderSystem.enableBlend();
            for (int i = 0; i < fullBubbles + burstingBubbles; i++) {
                boolean isBursting = i >= fullBubbles;
                guiGraphics.blitSprite(isBursting ? OXYGEN_BURSTING_SPRITE : OXYGEN_SPRITE,
                        xBase - i * spriteSize - spriteSize,
                        yBase, spriteSize, spriteSize);
            }
            RenderSystem.disableBlend();
        }

    }
}
