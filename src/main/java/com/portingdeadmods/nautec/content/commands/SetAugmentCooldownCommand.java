package com.portingdeadmods.nautec.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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

// /modjam ingredients set <slot> <augment>

// TODO: Only set ingredients for slots that support them
public class SetAugmentCooldownCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Nautec.MODID)
                .then(Commands.literal("ingredients")
                        .then(Commands.literal("cooldown")
                                .then(Commands.literal("set")
                                        .then(Commands.argument("slot", AugmentSlotArgumentType.getInstance())
                                                .then(Commands.argument("cooldown", IntegerArgumentType.integer())
                                                        .executes(SetAugmentCooldownCommand::execute)))))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        Player player = ctx.getSource().getPlayer();
        AugmentSlot slot = ctx.getArgument("slot", AugmentSlot.class);
        int cooldown = ctx.getArgument("cooldown", int.class);
        Augment augmentBySlot = AugmentHelper.getAugmentBySlot(player, slot);
        if (augmentBySlot != null) {
            augmentBySlot.setCooldown(cooldown);
            player.sendSystemMessage(Component.literal("Set augment cooldown in slot '" + slot.getName() + "' to: " + cooldown));
        }
        return 1;
    }

}
