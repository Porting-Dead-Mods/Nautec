package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.BioReactorBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.IncubatorBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class BioReactorMenu extends NTAbstractContainerMenu<BioReactorBlockEntity> {
    public BioReactorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (BioReactorBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public BioReactorMenu(int containerId, @NotNull Inventory inv, @NotNull BioReactorBlockEntity blockEntity) {
        super(NTMenuTypes.INCUBATOR.get(), containerId, inv, blockEntity);

    }

    @Override
    protected int getMergeableSlotCount() {
        return 2;
    }
}
