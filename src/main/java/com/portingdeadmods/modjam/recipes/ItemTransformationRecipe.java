package com.portingdeadmods.modjam.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.modjam.recipes.utils.IngredientWithCount;
import com.portingdeadmods.modjam.recipes.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ItemTransformationRecipe(List<IngredientWithCount> ingredients, ItemStack result) implements Recipe<MJRecipeInput> {
    public static final String NAME = "item_transformation";

    @Override
    public boolean matches(@NotNull MJRecipeInput recipeInput, @NotNull Level level) {
        List<ItemStack> inputItems = recipeInput.items().stream().filter(input -> !input.isEmpty()).toList();
        return RecipeUtils.compareItems(inputItems, ingredients);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MJRecipeInput input, HolderLookup.@NotNull Provider registries) {
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
        return RecipeUtils.listToNonNullList(RecipeUtils.iWCToIngredientsSaveCount(ingredients));
    }

    public @NotNull NonNullList<IngredientWithCount> getIngredientsWithCount() {
        return RecipeUtils.listToNonNullList(ingredients);
    }

    public static class Serializer implements RecipeSerializer<ItemTransformationRecipe> {
        public static final ItemTransformationRecipe.Serializer INSTANCE = new ItemTransformationRecipe.Serializer();
        private static final MapCodec<ItemTransformationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                IngredientWithCount.CODEC.listOf().fieldOf("ingredients").forGetter(ItemTransformationRecipe::ingredients),
                ItemStack.OPTIONAL_CODEC.fieldOf("result").forGetter(ItemTransformationRecipe::result)
        ).apply(builder, ItemTransformationRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, ItemTransformationRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC.apply(ByteBufCodecs.list()),
                ItemTransformationRecipe::ingredients,
                ItemStack.OPTIONAL_STREAM_CODEC,
                ItemTransformationRecipe::result,
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
