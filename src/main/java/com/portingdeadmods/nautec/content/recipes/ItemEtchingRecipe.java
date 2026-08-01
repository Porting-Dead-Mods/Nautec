package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ItemEtchingRecipe(IngredientWithCount ingredient, ItemStackTemplate resultTemplate, int duration) implements Recipe<SingleRecipeInput> {
    public static final String NAME = "item_etching";

    public @NotNull ItemStack result() {
        return resultTemplate.create();
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput, @NotNull Level level) {
        return ingredient.test(recipeInput.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input) {
        return resultTemplate.create();
    }

    public @NotNull ItemStack getResultItem(HolderLookup.@Nullable Provider registries) {
        return resultTemplate.create();
    }

    @Override
    public @NotNull String group() {
        return "";
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return RecipeUtils.listToNonNullList(List.of(RecipeUtils.iWCToIngredientSaveCount(ingredient)));
    }

    public @NotNull NonNullList<IngredientWithCount> getIngredientsWithCount() {
        return RecipeUtils.listToNonNullList(List.of(ingredient));
    }

    public static class Serializer {
        private static final MapCodec<ItemEtchingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                IngredientWithCount.CODEC.fieldOf("ingredient").forGetter(ItemEtchingRecipe::ingredient),
                ItemStackTemplate.CODEC.fieldOf("result").forGetter(ItemEtchingRecipe::resultTemplate),
                Codec.INT.fieldOf("duration").forGetter(ItemEtchingRecipe::duration)
        ).apply(builder, ItemEtchingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, ItemEtchingRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC,
                ItemEtchingRecipe::ingredient,
                ItemStackTemplate.STREAM_CODEC,
                ItemEtchingRecipe::resultTemplate,
                ByteBufCodecs.INT,
                ItemEtchingRecipe::duration,
                ItemEtchingRecipe::new
        );
        public static final RecipeSerializer<ItemEtchingRecipe> INSTANCE = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

        private Serializer() {
        }
    }

    public static class Type {
        public static final RecipeType<ItemEtchingRecipe> INSTANCE = RecipeType.simple(Nautec.rl(NAME));

        private Type() {
        }
    }
}
