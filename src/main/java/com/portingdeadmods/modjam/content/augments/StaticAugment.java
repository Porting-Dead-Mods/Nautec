package com.portingdeadmods.modjam.content.augments;

import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public interface StaticAugment {
    void breakBlock(BlockEvent.BreakEvent event);

    void tick(PlayerTickEvent.Post event);
}
