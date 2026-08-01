package com.portingdeadmods.nautec.content.recipes.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public record IngredientWithCount(Ingredient ingredient, int count) {
    public static final Codec<IngredientWithCount> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(IngredientWithCount::ingredient),
            Codec.INT.optionalFieldOf("count", 1).forGetter(IngredientWithCount::count)
    ).apply(builder, IngredientWithCount::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, IngredientWithCount> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            IngredientWithCount::ingredient,
            ByteBufCodecs.INT,
            IngredientWithCount::count,
            IngredientWithCount::new
    );

    public boolean test(ItemStack itemStack) {
        return ingredient.test(itemStack) && itemStack.getCount() >= count;
    }

    public static IngredientWithCount fromItemStack(ItemStack itemStack) {
        return new IngredientWithCount(Ingredient.of(itemStack.getItem()), itemStack.getCount());
    }

    public static IngredientWithCount fromItemTag(TagKey<Item> itemTagKey) {
        return fromItemTag(itemTagKey, 1);
    }

    public static IngredientWithCount fromItemTag(TagKey<Item> itemTagKey, int count) {
        return new IngredientWithCount(Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(itemTagKey)), count);
    }

    public static IngredientWithCount fromItemLike(ItemLike itemLike) {
        return new IngredientWithCount(Ingredient.of(itemLike), 1);
    }

    public static IngredientWithCount fromItemLike(ItemLike itemLike, int count) {
        return new IngredientWithCount(Ingredient.of(itemLike), count);
    }
}
