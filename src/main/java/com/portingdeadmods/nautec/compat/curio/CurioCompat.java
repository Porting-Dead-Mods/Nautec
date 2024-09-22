package com.portingdeadmods.nautec.compat.curio;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class CurioCompat {
    public static ItemStack getStackInSlot(Player player, Item item) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(item));
        return slotResult.isPresent() ? slotResult.get().stack() : ItemStack.EMPTY;
    }
}
