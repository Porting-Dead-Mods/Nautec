package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ItemEtchingRecipe(IngredientWithCount ingredient, ItemStack result, int duration) implements Recipe<SingleRecipeInput> {
    public static final String NAME = "item_etching";

    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput, @NotNull Level level) {
        return ingredient.test(recipeInput.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@Nullable Provider registries) {
        return result.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, RecipeUtils.iWCToIngredientSaveCount(ingredient));
    }

    public @NotNull NonNullList<IngredientWithCount> getIngredientsWithCount() {
        return NonNullList.of(IngredientWithCount.EMPTY, ingredient);
    }

    public static class Serializer implements RecipeSerializer<ItemEtchingRecipe> {
        public static final ItemEtchingRecipe.Serializer INSTANCE = new ItemEtchingRecipe.Serializer();
        private static final MapCodec<ItemEtchingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                IngredientWithCount.CODEC.fieldOf("ingredient").forGetter(ItemEtchingRecipe::ingredient),
                ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter(ItemEtchingRecipe::result),
                Codec.INT.fieldOf("duration").forGetter(ItemEtchingRecipe::duration)
        ).apply(builder, ItemEtchingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, ItemEtchingRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC,
                ItemEtchingRecipe::ingredient,
                ItemStack.OPTIONAL_STREAM_CODEC,
                ItemEtchingRecipe::result,
                ByteBufCodecs.INT,
                ItemEtchingRecipe::duration,
                ItemEtchingRecipe::new
        );

        private Serializer() {
        }

        @Override
        public @NotNull MapCodec<ItemEtchingRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ItemEtchingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Type implements RecipeType<ItemEtchingRecipe> {
        public static final ItemEtchingRecipe.Type INSTANCE = new ItemEtchingRecipe.Type();

        private Type() {
        }

        @Override
        public String toString() {
            return ItemEtchingRecipe.NAME;
        }
    }
}

