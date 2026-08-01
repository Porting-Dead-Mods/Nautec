package com.portingdeadmods.nautec.tags;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class NTTags {
    public static final class Items {
        public static final TagKey<Item> AQUATIC_CATALYST = ntTag("aquatic_catalyst");
        public static final TagKey<Item> AQUARINE_STEEL = ntTag("aquarine_steel");
        public static final TagKey<Item> CORALS = ntTag("corals");
        public static final TagKey<Item> REPAIRS_AQUARINE_TOOLS = ntTag("repairs_aquarine_tools");
        public static final TagKey<Item> REPAIRS_AQUARINE_ARMOR = ntTag("repairs_aquarine_armor");
        public static final TagKey<Item> REPAIRS_DIVING_SUIT = ntTag("repairs_diving_suit");
        public static final TagKey<Item> REPAIRS_PRISMARINE_ARMOR = ntTag("repairs_prismarine_armor");

        private static TagKey<Item> ntTag(String name) {
            return TagKey.create(Registries.ITEM, Nautec.rl(name));
        }
    }
}
