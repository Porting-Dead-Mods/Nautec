package com.portingdeadmods.modjam;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(ModJam.MODID)
public class ModJam {
    public static final String MODID = "modjam";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModJam(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, MJConfig.SPEC);
    }
}
