package com.portingdeadmods.modjam.content.menus;

import com.portingdeadmods.modjam.registries.MJMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class AugmentMenu extends AbstractContainerMenu {

    public AugmentMenu(Player player, int containerId) {
        super(MJMenuTypes.AUGMENTS.get(), containerId);
    }

    public AugmentMenu(int id, Inventory inventory, RegistryFriendlyByteBuf data) {
        this(inventory.player, id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
