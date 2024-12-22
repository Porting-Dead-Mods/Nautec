package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.tags.NTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider{

    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }


    // FIX THE DATA GATHERER FOR THIS CLASS PLEASE

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NTTags.Items.AQUATIC_CATALYST, Items.HEART_OF_THE_SEA);
        tag(NTTags.Items.AQUARINE_STEEL, NTItems.AQUARINE_STEEL_INGOT);;
        tag(ItemTags.AXES,NTItems.AQUARINE_AXE);
        tag(ItemTags.PICKAXES,NTItems.AQUARINE_PICKAXE);
        tag(ItemTags.SWORDS,NTItems.AQUARINE_SWORD);
        tag(ItemTags.SHOVELS,NTItems.AQUARINE_SHOVEL);
        tag(ItemTags.HOES,NTItems.AQUARINE_HOE);
        tag(ItemTags.HEAD_ARMOR_ENCHANTABLE,NTItems.AQUARINE_HELMET);
        tag(ItemTags.CHEST_ARMOR_ENCHANTABLE,NTItems.AQUARINE_CHESTPLATE);
        tag(ItemTags.LEG_ARMOR_ENCHANTABLE,NTItems.AQUARINE_LEGGINGS);
        tag(ItemTags.FOOT_ARMOR_ENCHANTABLE,NTItems.AQUARINE_BOOTS);

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
        IntrinsicTagAppender<Item> tag = tag(itemTagKey);
        for (ItemLike item : items) {
            tag.add(item.asItem());
        }
    }

    @SafeVarargs
    private void tag(TagKey<Item> itemTagKey, TagKey<Item>... items) {
        IntrinsicTagAppender<Item> tag = tag(itemTagKey);
        for (TagKey<Item> item : items) {
            tag.addTag(item);
        }
    }
}
