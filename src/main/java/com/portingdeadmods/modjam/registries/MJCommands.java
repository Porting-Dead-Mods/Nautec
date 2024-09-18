package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.commands.SetAugmentCommand;
import com.portingdeadmods.modjam.content.commands.GetAugmentCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = ModJam.MODID)
public final class MJCommands {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        GetAugmentCommand.register(event.getDispatcher());
        SetAugmentCommand.register(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
