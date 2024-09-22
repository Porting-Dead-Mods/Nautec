package com.portingdeadmods.nautec.registries;

import com.mojang.brigadier.CommandDispatcher;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.commands.*;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = Nautec.MODID)
public final class NTCommands {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        GetAugmentCommand.register(dispatcher);
        SetAugmentCommand.register(dispatcher);
        ClearAugmentsCommand.register(dispatcher);
        GetAugmentCooldownCommand.register(dispatcher);
        SetAugmentCooldownCommand.register(dispatcher);
        ConfigCommand.register(dispatcher);
    }
}
