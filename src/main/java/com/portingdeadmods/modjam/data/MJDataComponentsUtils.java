package com.portingdeadmods.modjam.data;

import net.minecraft.world.item.ItemStack;

public class MJDataComponentsUtils {

    public static void setOxygenLevels(ItemStack stack, Integer value) {
        stack.set(MJDataComponents.OXYGEN, value);
    }

    public static Integer getOxygenLevels(ItemStack stack) {
        return stack.getOrDefault(MJDataComponents.OXYGEN, 0);
    }

    public static void setAbilityStatus(ItemStack stack, Boolean value) {
        stack.set(MJDataComponents.ABILITY_ENABLED, value);
    }

    public static Boolean isAbilityEnabled(ItemStack stack) {
        return stack.getOrDefault(MJDataComponents.ABILITY_ENABLED, false);
    }

    public static float isAbilityEnabledNBT(ItemStack stack) {
        return stack.getOrDefault(MJDataComponents.ABILITY_ENABLED, false) ? 1 : 0;
    }

    public static void setInfusedStatus(ItemStack stack, Boolean value) {
        stack.set(MJDataComponents.IS_INFUSED, value);
    }

    public static Boolean isInfused(ItemStack stack) {
        return stack.getOrDefault(MJDataComponents.IS_INFUSED, false);
    }
}
