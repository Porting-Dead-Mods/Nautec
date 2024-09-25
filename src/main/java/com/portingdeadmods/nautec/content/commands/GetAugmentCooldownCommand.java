package com.portingdeadmods.nautec.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

// /modjam ingredients get <slot>
public class GetAugmentCooldownCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Nautec.MODID)
                .then(Commands.literal("ingredients")
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
