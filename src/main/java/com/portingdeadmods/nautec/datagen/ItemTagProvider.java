package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.tags.NTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends IntrinsicHolderTagsProvider<Item> {

    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ITEM, lookupProvider, item -> item.builtInRegistryHolder().key(), Nautec.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NTTags.Items.AQUATIC_CATALYST, Items.HEART_OF_THE_SEA);
        tag(NTTags.Items.AQUARINE_STEEL, NTItems.AQUARINE_STEEL_INGOT);
        tag(ItemTags.AXES, NTItems.AQUARINE_AXE);
        tag(ItemTags.PICKAXES, NTItems.AQUARINE_PICKAXE);
        tag(ItemTags.SWORDS, NTItems.AQUARINE_SWORD);
        tag(ItemTags.SHOVELS, NTItems.AQUARINE_SHOVEL);
        tag(ItemTags.HOES, NTItems.AQUARINE_HOE);
        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE, NTItems.AQUARINE_HELMET);
        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE, NTItems.AQUARINE_CHESTPLATE);
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE, NTItems.AQUARINE_LEGGINGS);
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE, NTItems.AQUARINE_BOOTS);

        tag(NTTags.Items.REPAIRS_AQUARINE_TOOLS, NTItems.AQUARINE_STEEL_INGOT);
        tag(NTTags.Items.REPAIRS_AQUARINE_ARMOR, NTItems.AQUARINE_STEEL_INGOT);
        tag(NTTags.Items.REPAIRS_DIVING_SUIT, Items.COPPER_INGOT);
        tag(NTTags.Items.REPAIRS_PRISMARINE_ARMOR, Items.PRISMARINE);

        this.tag(NTTags.Items.CORALS,
                Items.TUBE_CORAL,
                Items.BRAIN_CORAL,
                Items.BUBBLE_CORAL,
                Items.FIRE_CORAL,
                Items.HORN_CORAL,
                Items.TUBE_CORAL_FAN,
                Items.BRAIN_CORAL_FAN,
                Items.BUBBLE_CORAL_FAN,
                Items.FIRE_CORAL_FAN,
                Items.HORN_CORAL_FAN);

    }

    private void tag(TagKey<Item> itemTagKey, ItemLike... items) {
        TagAppender<Item, Item> tag = tag(itemTagKey);
        for (ItemLike item : items) {
            tag.add(item.asItem());
        }
    }

    @SafeVarargs
    private void tag(TagKey<Item> itemTagKey, TagKey<Item>... items) {
        TagAppender<Item, Item> tag = tag(itemTagKey);
        for (TagKey<Item> item : items) {
            tag.addTag(item);
        }
    }
}
