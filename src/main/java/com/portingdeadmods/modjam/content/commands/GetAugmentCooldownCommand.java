package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

// /modjam augments get <slot>
public class GetAugmentCooldownCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(ModJam.MODID)
                .then(Commands.literal("augments")
                        .then(Commands.literal("cooldown")
                                .then(Commands.literal("get")
                                        .then(Commands.argument("slot", AugmentSlotArgumentType.getInstance())
                                                .executes(GetAugmentCooldownCommand::execute))))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        Player player = ctx.getSource().getPlayer();
        AugmentSlot augmentSlot = ctx.getArgument("slot", AugmentSlot.class);
        Augment augmentBySlot = AugmentHelper.getAugmentBySlot(player, augmentSlot);
        int cooldown = 0;
        if (augmentBySlot != null) {
            cooldown = augmentBySlot.getCooldown();
            player.sendSystemMessage(Component.literal("Augment cooldown for slot '" + augmentSlot.getName() + "': " + cooldown));
        }
        return 1;
    }
}
