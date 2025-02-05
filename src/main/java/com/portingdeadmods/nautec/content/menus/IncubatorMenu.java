package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.IncubatorBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class IncubatorMenu extends NTMachineMenu<IncubatorBlockEntity> {
    public IncubatorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (IncubatorBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public IncubatorMenu(int containerId, @NotNull Inventory inv, @NotNull IncubatorBlockEntity blockEntity) {
        super(NTMenuTypes.INCUBATOR.get(), containerId, inv, blockEntity);

        // Nutrient
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 80, 49));
    }

    @Override
    protected int getMergeableSlotCount() {
        return 1;
    }
}
