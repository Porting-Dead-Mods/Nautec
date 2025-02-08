package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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

    public static ItemStack getMaxStatDish(ResourceKey<Bacteria> bac, HolderLookup.Provider lookup) {
        Bacteria bacteria = getBacteria(lookup, bac);
        ItemStack dish = new ItemStack(NTItems.PETRI_DISH.get());

        if (bacteria == null) {
            return dish;
        }

        dish.set(NTDataComponents.BACTERIA, new ComponentBacteriaStorage(
                new BacteriaInstance(bac, bacteria.maxInitialSize(), bacteria.stats().collapseMaxStats(), true)
        ));

        return dish;
    }

    /**
     * <b><i>THIS METHOD SHOULD ONLY BE USED CLIENT SIDE :3</i></b>
     */
    public static Component resourceTooltip(ResourceKey<Bacteria> key) {
        Bacteria bacteria = getBacteria(Minecraft.getInstance().level.registryAccess(), key);
        MutableComponent component = Component.literal("  Resource: ").withStyle(ChatFormatting.YELLOW);
        if (bacteria.resource() instanceof Bacteria.Resource.ItemResource(Item item)) component.append(
                item.getName(ItemStack.EMPTY).copy().withStyle(ChatFormatting.WHITE)
        );

        return component;
    }
}
