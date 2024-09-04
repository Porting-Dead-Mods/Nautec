package com.portingdeadmods.modjam.content.blocks;

import com.portingdeadmods.modjam.api.blocks.DisplayBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ExampleBlock extends Block implements DisplayBlock {
    public ExampleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> displayText(Level level, BlockPos blockPos, Player player) {
        return List.of(Component.literal("Hello").withStyle(ChatFormatting.WHITE), Component.literal("World").withStyle(ChatFormatting.WHITE));
    }

    @Override
    public boolean display(Level level, BlockPos blockPos, Player player) {
        return true;
    }
}
