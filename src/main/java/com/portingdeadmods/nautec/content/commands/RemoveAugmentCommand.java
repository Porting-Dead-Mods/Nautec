package com.portingdeadmods.nautec.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

// /nautec augments remove <slot>
public class RemoveAugmentCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> nautecCommand = Commands.literal(Nautec.MODID)
                .requires(player -> player.hasPermission(2));

        dispatcher.register(nautecCommand
                .then(Commands.literal("augments")
                        .then(Commands.literal("remove")
                                .then(Commands.argument("slot", AugmentSlotArgumentType.getInstance())
                                        .executes(RemoveAugmentCommand::execute)))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) return 0;
        
        AugmentSlot slot = ctx.getArgument("slot", AugmentSlot.class);
        Augment currentAugment = AugmentHelper.getAugmentBySlot(player, slot);
        
        if (currentAugment == null) {
            player.sendSystemMessage(Component.literal("No augment found in slot: " + slot.getName()));
            return 0;
        }
        
        // Remove the augment (this will handle syncing)
        AugmentHelper.removeAugment(player, slot);
        
        player.sendSystemMessage(Component.literal("Removed augment from slot: " + slot.getName()));
        return 1;
    }
}