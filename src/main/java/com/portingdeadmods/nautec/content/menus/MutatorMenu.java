package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.api.menu.slots.SlotBacteriaStorage;
import com.portingdeadmods.nautec.content.blockentities.MutatorBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MutatorMenu extends NTMachineMenu<MutatorBlockEntity> {
    public MutatorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (MutatorBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MutatorMenu(int containerId, @NotNull Inventory inv, @NotNull MutatorBlockEntity blockEntity) {
        super(NTMenuTypes.MUTATOR.get(), containerId, inv, blockEntity);
        // Input
        addBacteriaStorageSlot(new SlotBacteriaStorage(blockEntity.getBacteriaStorage(), 0, 32, 33));

        // Output
        addBacteriaStorageSlot(new SlotBacteriaStorage(blockEntity.getBacteriaStorage(), 1, 126, 33));

        // Catalyst
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 79, 61));
    }

    @Override
    protected int getMergeableSlotCount() {
        return 1;
    }
}
