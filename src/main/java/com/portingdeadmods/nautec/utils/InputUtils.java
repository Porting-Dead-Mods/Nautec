package com.portingdeadmods.nautec.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

public class InputUtils {
    public static boolean isKeyDown(int key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key);
    }
}
