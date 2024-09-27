package com.portingdeadmods.nautec.utils;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class Utils {
    public static IntList intArrayToList(int[] array) {
        return IntList.of(array);
    }

    public static <T> Component registryTranslation(Registry<T> registry, T registryObject) {
        ResourceLocation objLoc = registry.getKey(registryObject);
        return Component.translatable(registry.key().location().getPath() + "." + objLoc.getNamespace() + "." + objLoc.getPath());
    }
}
