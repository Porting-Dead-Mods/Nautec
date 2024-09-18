package com.portingdeadmods.modjam.registries;

import com.mojang.brigadier.CommandDispatcher;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.commands.GetAugmentCooldownCommand;
import com.portingdeadmods.modjam.content.commands.SetAugmentCommand;
import com.portingdeadmods.modjam.content.commands.GetAugmentCommand;
import com.portingdeadmods.modjam.content.commands.SetAugmentCooldownCommand;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = ModJam.MODID)
public final class MJCommands {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        GetAugmentCommand.register(dispatcher);
        SetAugmentCommand.register(dispatcher);
        GetAugmentCooldownCommand.register(dispatcher);
        SetAugmentCooldownCommand.register(dispatcher);
        ConfigCommand.register(dispatcher);
    }
}
