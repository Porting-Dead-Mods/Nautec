package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentTypeArgumentType;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

// /modjam augments set <slot> <augment>

// TODO: Only set augments for slots that support them
public class SetAugmentCooldownCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(ModJam.MODID)
                .then(Commands.literal("augments")
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
