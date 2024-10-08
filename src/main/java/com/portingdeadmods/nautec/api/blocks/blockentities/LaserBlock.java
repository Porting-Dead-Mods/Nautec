package com.portingdeadmods.nautec.api.blocks.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.blocks.DisplayBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class LaserBlock extends ContainerBlock implements DisplayBlock {
    public LaserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        LaserBlockEntity laserBE = (LaserBlockEntity) level.getBlockEntity(blockPos);
        return List.of(
                Component.literal("Power: " + laserBE.getPower()).withStyle(ChatFormatting.WHITE),
                Component.literal("Purity: " + laserBE.getPurity()).withStyle(ChatFormatting.WHITE)
        );
    }
}
