package com.portingdeadmods.nautec.data;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NTDataComponentsUtils {

    public static void setOxygenLevels(ItemStack stack, Integer value) {
        stack.set(NTDataComponents.OXYGEN, value);
    }

    public static Integer getOxygenLevels(ItemStack stack) {
        return stack.getOrDefault(NTDataComponents.OXYGEN, 0);
    }

    public static void setAbilityStatus(ItemStack stack, Boolean value) {
        stack.set(NTDataComponents.ABILITY_ENABLED, value);
    }

    public static Boolean isAbilityEnabled(ItemStack stack) {
        return stack.getOrDefault(NTDataComponents.ABILITY_ENABLED, false);
    }

    public static float isAbilityEnabledNBT(ItemStack stack) {
        return stack.getOrDefault(NTDataComponents.ABILITY_ENABLED, false) ? 1 : 0;
    }

    public static float hasBacteria(ItemStack stack) {
        return stack.get(NTDataComponents.BACTERIA).bacteriaInstance().getBacteria() != NTBacterias.EMPTY ? 1 : 0;
    }

    public static void setInfusedStatus(ItemStack stack, Boolean value) {
        stack.set(NTDataComponents.IS_INFUSED, value);
    }

    public static Boolean isInfused(ItemStack stack) {
        return stack.getOrDefault(NTDataComponents.IS_INFUSED, false);
    }
}
