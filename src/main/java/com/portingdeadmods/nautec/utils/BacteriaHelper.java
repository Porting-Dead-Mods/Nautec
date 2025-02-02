package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public final class BacteriaHelper {
    public static Bacteria getBacteria(HolderLookup.Provider lookup, ResourceKey<Bacteria> bacteriaType) {
        Optional<HolderGetter<Bacteria>> lookup1 = lookup.asGetterLookup().lookup(NTRegistries.BACTERIA_KEY);
        return lookup1.map(bacteriaHolderGetter -> bacteriaHolderGetter.getOrThrow(bacteriaType).value())
                .orElse(null);
    }
}
