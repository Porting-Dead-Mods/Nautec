package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AugmentationStationMenu extends NTAbstractContainerMenu<AugmentationStationBlockEntity> {
    public AugmentationStationMenu(int containerId, @NotNull Inventory inv, @NotNull FriendlyByteBuf byteBuf) {
        this(containerId, inv, (AugmentationStationBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

    public AugmentationStationMenu(int containerId, @NotNull Inventory inv, @NotNull AugmentationStationBlockEntity blockEntity) {
        super(NTMenuTypes.AUGMENT_STATION.get(), containerId, inv, blockEntity);
    }

    @Override
    protected boolean supportsShiftClick(Player player, int index) {
        return false;
    }

    @Override
    protected int getMergeableSlotCount() {
        return 0;
    }
}
