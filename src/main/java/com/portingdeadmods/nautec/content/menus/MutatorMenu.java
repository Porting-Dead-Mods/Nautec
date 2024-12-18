package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.MutatorBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.multiblock.part.AugmentationStationExtensionBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MutatorMenu extends NTAbstractContainerMenu<MutatorBlockEntity> {
    public MutatorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (MutatorBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MutatorMenu(int containerId, @NotNull Inventory inv, @NotNull MutatorBlockEntity blockEntity) {
        super(NTMenuTypes.MUTATOR.get(), containerId, inv, blockEntity);

        // Catalyst
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 80, 35));

        addPlayerInventory(inv, 84);
        addPlayerHotbar(inv, 142);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 1;
    }
}
