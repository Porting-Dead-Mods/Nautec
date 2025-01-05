package com.portingdeadmods.nautec.api.menu;

import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.menu.slots.SlotBacteriaStorage;
import com.portingdeadmods.nautec.api.menu.slots.SlotFluidHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public abstract class NTMachineMenu<T extends ContainerBlockEntity> extends NTAbstractContainerMenu<T> {
    private final NonNullList<SlotFluidHandler> fluidTankSlots;
    private final NonNullList<SlotBacteriaStorage> bacteriaStorageSlots;

    public NTMachineMenu(MenuType<?> menuType, int containerId, @NotNull Inventory inv, @NotNull T blockEntity) {
        super(menuType, containerId, inv, blockEntity);

        addPlayerInventory(inv, 92);
        addPlayerHotbar(inv, 150);

        this.fluidTankSlots = NonNullList.create();
        this.bacteriaStorageSlots = NonNullList.create();
    }

    public void addFluidHandlerSlot(SlotFluidHandler slot) {
        this.fluidTankSlots.add(slot);
    }

    public void addBacteriaStorageSlot(SlotBacteriaStorage slot) {
        this.bacteriaStorageSlots.add(slot);
    }

    public NonNullList<SlotFluidHandler> getFluidTankSlots() {
        return fluidTankSlots;
    }

    public NonNullList<SlotBacteriaStorage> getBacteriaStorageSlots() {
        return bacteriaStorageSlots;
    }
}
