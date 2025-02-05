package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;

public final class BacteriaHelper {
    public static Bacteria getBacteria(HolderLookup.Provider lookup, ResourceKey<Bacteria> bacteriaType) {
        Optional<HolderGetter<Bacteria>> lookup1 = lookup.asGetterLookup().lookup(NTRegistries.BACTERIA_KEY);
        return lookup1.map(bacteriaHolderGetter -> bacteriaHolderGetter.getOrThrow(bacteriaType).value())
                .orElse(null);
    }

    public static List<Bacteria> getBacteriaList(HolderLookup.Provider lookup) {
        return lookup.lookup(NTRegistries.BACTERIA_KEY).get().listElements().map(Holder.Reference::value).toList();
    }

    public static List<ResourceKey<Bacteria>> getBacteriaKeys(HolderLookup.Provider lookup) {
        return lookup.lookup(NTRegistries.BACTERIA_KEY).get().listElementIds().toList();
    }
}
