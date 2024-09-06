package com.portingdeadmods.modjam.api.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public interface DisplayBlock {
    List<Component> displayText(Level level, BlockPos blockPos, Player player);

    default boolean display(Level level, BlockPos blockPos, Player player) {
        return true;
    }
}
