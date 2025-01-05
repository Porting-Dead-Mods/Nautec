package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.api.menu.slots.SlotFluidHandler;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
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

        addFluidHandlerSlot(new SlotFluidHandler(blockEntity.getFluidHandler(), 0, 80, 20, 20, 20));
        addFluidHandlerSlot(new SlotFluidHandler(blockEntity.getSecondaryFluidHandler(), 0, 80, 60, 20, 20));
    }

    @Override
    protected int getMergeableSlotCount() {
        return 4;
    }
}
