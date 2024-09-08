package com.portingdeadmods.modjam.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class InputUtils {
    public static boolean isKeyDown(int key){
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(),key);
    }
}
