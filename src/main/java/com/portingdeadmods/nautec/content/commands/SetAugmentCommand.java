package com.portingdeadmods.nautec.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentTypeArgumentType;
import com.portingdeadmods.nautec.network.SyncAugmentPayload;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

// /modjam augments set <slot> <augment>

// TODO: Only set augments for slots that support them
public class SetAugmentCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Nautec.MODID)
                .then(Commands.literal("augments")
                        .then(Commands.literal("set")
                                .then(Commands.argument("slot", AugmentSlotArgumentType.getInstance())
                                        .then(Commands.argument("augment", AugmentTypeArgumentType.getInstance())
                                                .executes(SetAugmentCommand::execute))))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        AugmentSlot slot = ctx.getArgument("slot", AugmentSlot.class);
        Augment currentAug = AugmentHelper.getAugmentBySlot(player, slot);
        if (currentAug != null) {
            currentAug.onRemoved(player);
        }
        Augment augment = AugmentHelper.createAugment(ctx.getArgument("augment", AugmentType.class), player, slot);
        PacketDistributor.sendToPlayer(player, new SyncAugmentPayload(augment, new CompoundTag()));
        player.sendSystemMessage(Component.literal("Set augment in slot '" + slot.getName() + "' to: " + augment.getAugmentType()));
        return 1;
    }

}
