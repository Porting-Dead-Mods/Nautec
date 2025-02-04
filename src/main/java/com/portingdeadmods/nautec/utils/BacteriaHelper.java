package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.bacteria.BacteriaMutation;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.Optional;

public final class BacteriaHelper {
    public static Bacteria getBacteria(HolderLookup.Provider lookup, ResourceKey<Bacteria> bacteriaType) {
        Optional<HolderGetter<Bacteria>> lookup1 = lookup.asGetterLookup().lookup(NTRegistries.BACTERIA_KEY);
        return lookup1.map(bacteriaHolderGetter -> bacteriaHolderGetter.getOrThrow(bacteriaType).value())
                .orElse(null);
    }

    public static BacteriaInstance rollBacteria(HolderLookup.Provider lookup, BacteriaInstance instance, Item catalyst) {
        Bacteria bacteria = getBacteria(lookup, instance.getBacteria());
        for (BacteriaMutation mutation : bacteria.mutations()) {
            if (mutation.catalyst() == catalyst) {
                if (Math.random() * 100 < mutation.chance()) {
                    return new BacteriaInstance(mutation.bacteria(), instance.getAmount(), getBacteria(lookup, mutation.bacteria()).stats().collapse());
                }
            }
        }
        return instance.rollStats();
    }
}
