package com.portingdeadmods.nautec.registries;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.nautec.Nautec;
import net.neoforged.neoforge.common.util.Lazy;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class NTKeybinds {
    public static final Lazy<KeyMapping> AUGMENT_SCREEN_KEYBIND = Lazy.of(() -> new KeyMapping(
            "Open the Augmentation Screen",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "NauTec"
    ));

    public static final Lazy<KeyMapping> GIVE_DIAMOND_KEYBIND = Lazy.of(() -> new KeyMapping(
            "Give Diamond",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "NauTec")
    );

    public static final Lazy<KeyMapping> THROW_TRIDENT_KEYBIND = Lazy.of(() -> new KeyMapping(
            "Throw Trident",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "NauTec")
    );

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(AUGMENT_SCREEN_KEYBIND.get());
        event.register(GIVE_DIAMOND_KEYBIND.get());
        event.register(THROW_TRIDENT_KEYBIND.get());
    }
}
