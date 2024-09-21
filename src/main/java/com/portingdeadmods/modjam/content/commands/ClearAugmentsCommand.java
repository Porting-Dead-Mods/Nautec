package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.modjam.data.MJDataAttachments;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// /modjam augments clear
public class ClearAugmentsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(ModJam.MODID)
                .then(Commands.literal("augments")
                        .then(Commands.literal("clear").executes(ClearAugmentsCommand::execute))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        Player player = ctx.getSource().getPlayer();
        player.setData(MJDataAttachments.AUGMENTS, new HashMap<>());
        player.setData(MJDataAttachments.AUGMENTS_EXTRA_DATA, new HashMap<>());
        player.sendSystemMessage(Component.literal("Cleared all player augments"));
        return 1;
    }
}
