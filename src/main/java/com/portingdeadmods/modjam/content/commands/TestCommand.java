package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.modjam.ModJam;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;

public class TestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(ModJam.MODID)
                .then(Commands.literal("test")
                        .then(Commands.literal("stuff")
                                .executes(TestCommand::test))
                )
        );
    }

    private static int test(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        ServerLevel level = source.getLevel();
        source.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.system("Level: " + level)), false, ChatType.bind(ChatType.CHAT, source));
        return 1;
    }
}
