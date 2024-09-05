package com.portingdeadmods.modjam.tags;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class MJTags {
    public static final class Items {
        public static final TagKey<Item> AQUATIC_CATALYST = mjTag("aquatic_catalyst");

        private static TagKey<Item> mjTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ModJam.MODID, name));
        }
    }
}
