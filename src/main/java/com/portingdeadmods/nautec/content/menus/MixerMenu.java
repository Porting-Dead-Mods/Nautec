package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.MixerBlockEntity;
import com.portingdeadmods.nautec.content.blockentities.MutatorBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class MixerMenu extends NTAbstractContainerMenu<MixerBlockEntity> {
    public MixerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (MixerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MixerMenu(int containerId, @NotNull Inventory inv, @NotNull MixerBlockEntity blockEntity) {
        super(NTMenuTypes.MIXER.get(), containerId, inv, blockEntity);

        addPlayerInventory(inv, 92);
        addPlayerHotbar(inv, 150);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 4;
    }
}
