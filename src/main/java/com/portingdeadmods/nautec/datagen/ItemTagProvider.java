package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.tags.NTTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider{

    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }


    // FIX THE DATA GATHERER FOR THIS CLASS PLEASE

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NTTags.Items.AQUATIC_CATALYST, Items.HEART_OF_THE_SEA);
        tag(NTTags.Items.AQUARINE_STEEL, NTItems.AQUARINE_STEEL_INGOT);
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
