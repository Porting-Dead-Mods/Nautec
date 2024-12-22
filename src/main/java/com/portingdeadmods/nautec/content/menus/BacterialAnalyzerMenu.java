package com.portingdeadmods.nautec.content.menus;

import com.portingdeadmods.nautec.api.menu.NTAbstractContainerMenu;
import com.portingdeadmods.nautec.content.blockentities.BacterialAnalyzerBlockEntity;
import com.portingdeadmods.nautec.registries.NTMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class BacterialAnalyzerMenu extends NTAbstractContainerMenu<BacterialAnalyzerBlockEntity> {
    public BacterialAnalyzerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (BacterialAnalyzerBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public BacterialAnalyzerMenu(int containerId, @NotNull Inventory inv, @NotNull BacterialAnalyzerBlockEntity blockEntity) {
        super(NTMenuTypes.BACTERIAL_ANALYZER.get(), containerId, inv, blockEntity);

        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 53, 38));
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 1, 107, 38));

        addPlayerInventory(inv, 84);
        addPlayerHotbar(inv, 142);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 2;
    }
}
