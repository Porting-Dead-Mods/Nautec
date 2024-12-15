package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;

public final class BacteriaHelper {
    public static Bacteria getBacteria(HolderLookup.Provider lookup, ResourceKey<Bacteria> bacteriaType) {
        return lookup.asGetterLookup().lookupOrThrow(NTRegistries.BACTERIA_KEY).getOrThrow(bacteriaType).value();
    }
}
