package com.portingdeadmods.modjam.utils;

import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;

public final class ItemUtils {
    public static final int ITEM_POWER_INPUT = 128;

    public static final int POWER_BAR_COLOR = FastColor.ARGB32.color(94, 133, 164);

    public static int powerForDurabilityBar(ItemStack itemStack) {
        IPowerStorage powerStorage = itemStack.getCapability(MJCapabilities.PowerStorage.ITEM);
        if (powerStorage != null) {
            int powerStored = powerStorage.getPowerStored();
            int powerCapacity = powerStorage.getPowerCapacity();
            float chargeRatio = (float) powerStored / powerCapacity;
            return Math.round(13.0F - ((1 - chargeRatio) * 13.0F));
        }
        return 0;
    }
}
