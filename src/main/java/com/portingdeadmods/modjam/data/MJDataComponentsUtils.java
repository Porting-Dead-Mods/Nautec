package com.portingdeadmods.modjam.data;

import net.minecraft.world.item.ItemStack;

public class MJDataComponentsUtils {

    public static void setOxygenLevels(ItemStack stack, Integer value) {
        stack.set(MJDataComponents.OXYGEN, value);
    }

    public static Integer getOxygenLevels(ItemStack stack) {
        return stack.getOrDefault(MJDataComponents.OXYGEN, 0);
    }

}
