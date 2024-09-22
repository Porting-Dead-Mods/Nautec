package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class DisallowBreakingAugment extends Augment {
    public DisallowBreakingAugment(AugmentSlot augmentSlot) {
        super(NTAugments.DISALLOW_BREAKING.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }

    @Override
    public void breakBlock(BlockEvent.BreakEvent event) {
        event.setCanceled(true);
    }
}
