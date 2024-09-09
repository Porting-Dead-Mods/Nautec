package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.content.augments.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class SetAugmentIdCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("set_augment")
                .then(Commands.argument("slotId", IntegerArgumentType.integer(0,6))
                .then(Commands.argument("augId", IntegerArgumentType.integer(0,8))
                .executes(SetAugmentIdCommand::execute))));
    }
    private static int execute(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        ServerLevel level = source.getLevel();
        Player player =  ctx.getSource().getPlayer();
        int slotId = IntegerArgumentType.getInteger(ctx, "slotId");
        int augId = IntegerArgumentType.getInteger(ctx, "augId");
        AugmentHelper.setIdAndUpdate(player, Slot.GetValue(slotId), augId);
        return 1;
    }

}
