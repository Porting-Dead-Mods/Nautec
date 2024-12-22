package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.content.recipes.inputs.ItemTransformationRecipeInput;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record ItemTransformationRecipe(IngredientWithCount ingredient, ItemStack result, int duration,
                                       float purity) implements Recipe<ItemTransformationRecipeInput> {
    public static final String NAME = "item_transformation";

    @Override
    public boolean matches(@NotNull ItemTransformationRecipeInput recipeInput, @NotNull Level level) {
        return ingredient.test(recipeInput.item()) && purity <= recipeInput.purity();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull ItemTransformationRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
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

    public static class Serializer implements RecipeSerializer<ItemTransformationRecipe> {
        public static final ItemTransformationRecipe.Serializer INSTANCE = new ItemTransformationRecipe.Serializer();
        private static final MapCodec<ItemTransformationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                IngredientWithCount.CODEC.fieldOf("ingredient").forGetter(ItemTransformationRecipe::ingredient),
                ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter(ItemTransformationRecipe::result),
                Codec.INT.fieldOf("duration").forGetter(ItemTransformationRecipe::duration),
                Codec.FLOAT.fieldOf("purity").forGetter(ItemTransformationRecipe::purity)
        ).apply(builder, ItemTransformationRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, ItemTransformationRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC,
                ItemTransformationRecipe::ingredient,
                ItemStack.OPTIONAL_STREAM_CODEC,
                ItemTransformationRecipe::result,
                ByteBufCodecs.INT,
                ItemTransformationRecipe::duration,
                ByteBufCodecs.FLOAT,
                ItemTransformationRecipe::purity,
                ItemTransformationRecipe::new
        );

        private Serializer() {
        }

        @Override
        public @NotNull MapCodec<ItemTransformationRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ItemTransformationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Type implements RecipeType<ItemTransformationRecipe> {
        public static final ItemTransformationRecipe.Type INSTANCE = new ItemTransformationRecipe.Type();

        private Type() {
        }

        @Override
        public String toString() {
            return ItemTransformationRecipe.NAME;
        }
    }
}
