package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import net.neoforged.neoforge.event.level.BlockEvent;

public class DisallowBreakingAugment extends Augment{
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void breakBlock(Slot slot, BlockEvent.BreakEvent event) {
        event.setCanceled(true);
    }
}
