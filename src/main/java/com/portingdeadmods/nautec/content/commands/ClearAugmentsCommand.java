package com.portingdeadmods.nautec.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

// /modjam augments clear
public class ClearAugmentsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> nautecCommand = Commands.literal(Nautec.MODID)
                .requires(player -> player.hasPermission(2));

        dispatcher.register(nautecCommand
                .then(Commands.literal("augments")
                        .then(Commands.literal("clear").executes(ClearAugmentsCommand::execute))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        Player player = ctx.getSource().getPlayer();
        for (Augment augment : AugmentHelper.getAugments(player).values()) {
            augment.onRemoved(ctx.getSource().getPlayer());
        }
        player.setData(NTDataAttachments.AUGMENTS, new HashMap<>());
        player.setData(NTDataAttachments.AUGMENTS_EXTRA_DATA, new HashMap<>());
        player.sendSystemMessage(Component.literal("Cleared all player augments"));
        return 1;
    }
}
