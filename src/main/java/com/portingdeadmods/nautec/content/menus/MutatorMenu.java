package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.MutatorBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MutatorMenu extends NTAbstractContainerMenu<MutatorBlockEntity> {
    public MutatorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (MutatorBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MutatorMenu(int containerId, @NotNull Inventory inv, @NotNull MutatorBlockEntity blockEntity) {
        super(NTMenuTypes.MUTATOR.get(), containerId, inv, blockEntity);
        // Input
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 25, 25));

        // Output
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 1, 133, 25));

        // Catalyst
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 2, 79, 54));

        addPlayerInventory(inv, 84);
        addPlayerHotbar(inv, 142);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 2;
    }
}
