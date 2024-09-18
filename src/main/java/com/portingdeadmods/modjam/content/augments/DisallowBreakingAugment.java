package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.registries.MJAugments;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.level.BlockEvent;

public class DisallowBreakingAugment extends Augment {
    public DisallowBreakingAugment(AugmentSlot augmentSlot) {
        super(MJAugments.DISALLOW_BREAKING.get(), augmentSlot);
    }

    @Override
    public void breakBlock(BlockEvent.BreakEvent event) {
        event.setCanceled(true);
    }
}
