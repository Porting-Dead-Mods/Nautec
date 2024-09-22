package com.portingdeadmods.nautec.tags;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class MJTags {
    public static final class Items {
        public static final TagKey<Item> AQUATIC_CATALYST = mjTag("aquatic_catalyst");
        public static final TagKey<Item> AQUARINE_STEEL = mjTag("aquarine_steel");

        private static TagKey<Item> mjTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, name));
        }
    }
}
