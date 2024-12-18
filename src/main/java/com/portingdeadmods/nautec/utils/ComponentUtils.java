package com.portingdeadmods.nautec.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class ComponentUtils {
    public static MutableComponent colored(String text, ChatFormatting col) {
        return Component.literal(text).withStyle(col);
    }

    public static MutableComponent countableStatShow(String name, Number val, Number max) {
        MutableComponent mc1 = colored(name, ChatFormatting.YELLOW)
                .append(Component.literal(": ").withStyle(ChatFormatting.WHITE));

        if (val.doubleValue() < max.doubleValue()) {
            mc1.append(colored(val.toString(), ChatFormatting.GREEN));
        } else {
            mc1.append(colored(val.toString(), ChatFormatting.RED));
        }

        return mc1;
    }

    public static MutableComponent stringStatShow(String name, String val) {
        return colored(name, ChatFormatting.YELLOW)
                .append(Component.literal(": ").withStyle(ChatFormatting.WHITE))
                .append(colored(val, ChatFormatting.GREEN));
    }
}
