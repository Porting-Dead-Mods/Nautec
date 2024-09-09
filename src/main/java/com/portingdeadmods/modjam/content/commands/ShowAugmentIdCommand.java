package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class ShowAugmentIdCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("show_augments")
                                .executes(ShowAugmentIdCommand::execute));
    }
    private static int execute(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        ServerLevel level = source.getLevel();
        Player player =  ctx.getSource().getPlayer();
        player.sendSystemMessage(Component.literal("Head Id = " + AugmentHelper.getId(player, Slot.HEAD)));
        player.sendSystemMessage(Component.literal("Body Id = " + AugmentHelper.getId(player, Slot.BODY)));
        return 1;
    }
}
