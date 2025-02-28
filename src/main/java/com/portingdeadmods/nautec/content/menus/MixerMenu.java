package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.api.menu.slots.SlotFluidHandler;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MixerMenu extends NTMachineMenu<MixerBlockEntity> {
    public MixerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (MixerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MixerMenu(int containerId, @NotNull Inventory inv, @NotNull MixerBlockEntity blockEntity) {
        super(NTMenuTypes.MIXER.get(), containerId, inv, blockEntity);

        for (int i = 0; i < 4; i++) {
            addSlot(new SlotItemHandler(blockEntity.getItemHandler(), i, 29 + i * (4 + 18), 12));
        }

        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 4, 29 + 2 * (4 + 18) - 11, 67));

        addFluidHandlerSlot(new SlotFluidHandler(blockEntity.getFluidHandler(), 0, 122, 11, 18, 18));
        addFluidHandlerSlot(new SlotFluidHandler(blockEntity.getSecondaryFluidHandler(), 0, 122, 66, 18, 18));
    }

    @Override
    protected int getMergeableSlotCount() {
        return 4;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            ItemStack copy = stack.copy();

            int playerInventoryStart = 0;
            int hotbarStart = 27;
            int hotbarEnd = 36;
            int machineSlotStart = 36;
            int machineSlotEnd = 41;

            if (index < hotbarEnd) {
                // Move from player inventory/hotbar to block slots
                if (!moveItemStackTo(stack, machineSlotStart, machineSlotEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Move from block slots to player inventory
                if (!moveItemStackTo(stack, playerInventoryStart, hotbarEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, stack);
            return copy;
        }
        return ItemStack.EMPTY;
    }


}
