package com.portingdeadmods.nautec.tags;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class NTTags {
    public static final class Items {
        public static final TagKey<Item> AQUATIC_CATALYST = ntTag("aquatic_catalyst");
        public static final TagKey<Item> AQUARINE_STEEL = ntTag("aquarine_steel");
        public static final TagKey<Item> CORALS = ntTag("corals");

        private static TagKey<Item> ntTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, name));
        }
    }
}
