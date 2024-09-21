package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.Augment;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.modjam.content.commands.arguments.AugmentTypeArgumentType;
import com.portingdeadmods.modjam.network.SyncAugmentPayload;
import com.portingdeadmods.modjam.utils.AugmentHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

// /modjam augments set <slot> <augment>

// TODO: Only set augments for slots that support them
public class SetAugmentCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(ModJam.MODID)
                .then(Commands.literal("augments")
                        .then(Commands.literal("set")
                                .then(Commands.argument("slot", AugmentSlotArgumentType.getInstance())
                                        .then(Commands.argument("augment", AugmentTypeArgumentType.getInstance())
                                                .executes(SetAugmentCommand::execute))))));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        AugmentSlot slot = ctx.getArgument("slot", AugmentSlot.class);
        AugmentType<?> type = ctx.getArgument("augment", AugmentType.class);
        Augment augment = type.create(slot);
        augment.setPlayer(player);
        AugmentHelper.setAugment(player, slot, augment);
        PacketDistributor.sendToPlayer(player, new SyncAugmentPayload(augment, new CompoundTag()));
        player.sendSystemMessage(Component.literal("Set augment in slot '" + slot.getName() + "' to: " + type));
        return 1;
    }

}
