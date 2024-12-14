package com.portingdeadmods.nautec.api.items;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public interface ICurioItem {
    void curioTick(ItemStack itemStack, SlotContext slotContext);
}
