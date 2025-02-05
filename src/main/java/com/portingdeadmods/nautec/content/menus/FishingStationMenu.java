package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTMachineMenu;
import com.portingdeadmods.nautec.content.blockentities.FishingStationBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FishingStationMenu extends NTMachineMenu<FishingStationBlockEntity> {
    public FishingStationMenu(int containerId, @NotNull Inventory inv, @NotNull FishingStationBlockEntity blockEntity) {
        super(NTMenuTypes.FISHING_STATION.get(), containerId, inv, blockEntity);
        int x = 45;
        int y = 20;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                addSlot(new SlotItemHandler(blockEntity.getItemHandler(), j * 5 + i, x + i * 18, y + j * 18));
            }
        }
    }

    public FishingStationMenu(int i, Inventory inventory, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        this(i,inventory, (FishingStationBlockEntity) inventory.player.level().getBlockEntity(registryFriendlyByteBuf.readBlockPos()));
    }

    @Override
    protected int getMergeableSlotCount() {
        return 5 * 3;
    }
}
