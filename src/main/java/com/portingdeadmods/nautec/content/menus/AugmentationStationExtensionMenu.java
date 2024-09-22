package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class AugmentationStationExtensionMenu extends NTAbstractContainerMenu<AugmentationStationExtensionBlockEntity> {
    public AugmentationStationExtensionMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (AugmentationStationExtensionBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public AugmentationStationExtensionMenu(int containerId, @NotNull Inventory inv, @NotNull AugmentationStationExtensionBlockEntity blockEntity) {
        super(NTMenuTypes.AUGMENT_STATION_EXTENSION.get(), containerId, inv, blockEntity);
        // Augment
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 176 / 2 - 8, 48));
        // Robot Arm
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 1, 176 / 2 - 8, 26));
        addPlayerInventory(inv, 84);
        addPlayerHotbar(inv, 142);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 2;
    }
}
