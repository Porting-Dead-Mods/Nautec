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
    public static final Lazy<KeyMapping> AUGMENT_SCREEN_KEYBIND = keyBind(
            "Open the Augmentation Screen", GLFW.GLFW_KEY_B);

    // public static final Lazy<KeyMapping> GIVE_DIAMOND_KEYBIND = keyBind(
    //         "Give Diamond", GLFW.GLFW_KEY_N);

    public static final Lazy<KeyMapping> THROW_TRIDENT_KEYBIND = keyBind(
            "Throw Trident", GLFW.GLFW_KEY_Y);

    public static final Lazy<KeyMapping> LEAP_KEYBIND = keyBind(
            "Leap", GLFW.GLFW_KEY_LEFT_ALT);

    public static final Lazy<KeyMapping> THROW_POTION_KEYBIND = keyBind(
            "Throw Potion", GLFW.GLFW_KEY_G);

    public static final Lazy<KeyMapping> THROW_SPREADING_KEYBIND = keyBind(
            "Throw Spreading Trident", GLFW.GLFW_KEY_Y);

    public static final Lazy<KeyMapping> ACTIVATE_LASER_KEYBIND = keyBind(
            "Activate Guardian Eye Augment Laser", GLFW.GLFW_KEY_L);


    public static Lazy<KeyMapping> keyBind(String name, int key) {
        return Lazy.of(() -> new KeyMapping(name, InputConstants.Type.KEYSYM, key, Nautec.MODNAME));
    }
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(AUGMENT_SCREEN_KEYBIND.get());
        //event.register(GIVE_DIAMOND_KEYBIND.get());
        event.register(THROW_TRIDENT_KEYBIND.get());
        event.register(LEAP_KEYBIND.get());
        event.register(THROW_POTION_KEYBIND.get());
        event.register(THROW_SPREADING_KEYBIND.get());
        event.register(ACTIVATE_LASER_KEYBIND.get());
    }
}
