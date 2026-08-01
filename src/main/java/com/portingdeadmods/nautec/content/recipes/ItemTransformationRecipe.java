package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.inputs.ItemTransformationRecipeInput;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ItemTransformationRecipe(IngredientWithCount ingredient, ItemStackTemplate resultTemplate, int duration,
                                       float purity) implements Recipe<ItemTransformationRecipeInput> {
    public static final String NAME = "item_transformation";

    public @NotNull ItemStack result() {
        return resultTemplate.create();
    }

    @Override
    public boolean matches(@NotNull ItemTransformationRecipeInput recipeInput, @NotNull Level level) {
        return ingredient.test(recipeInput.item()) && purity <= recipeInput.purity();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull ItemTransformationRecipeInput input) {
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
    public @NotNull RecipeSerializer<? extends Recipe<ItemTransformationRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<ItemTransformationRecipeInput>> getType() {
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

    public static class Serializer {
        private static final MapCodec<ItemTransformationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                IngredientWithCount.CODEC.fieldOf("ingredient").forGetter(ItemTransformationRecipe::ingredient),
                ItemStackTemplate.CODEC.fieldOf("result").forGetter(ItemTransformationRecipe::resultTemplate),
                Codec.INT.fieldOf("duration").forGetter(ItemTransformationRecipe::duration),
                Codec.FLOAT.fieldOf("purity").forGetter(ItemTransformationRecipe::purity)
        ).apply(builder, ItemTransformationRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, ItemTransformationRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC,
                ItemTransformationRecipe::ingredient,
                ItemStackTemplate.STREAM_CODEC,
                ItemTransformationRecipe::resultTemplate,
                ByteBufCodecs.INT,
                ItemTransformationRecipe::duration,
                ByteBufCodecs.FLOAT,
                ItemTransformationRecipe::purity,
                ItemTransformationRecipe::new
        );
        public static final RecipeSerializer<ItemTransformationRecipe> INSTANCE = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

        private Serializer() {
        }
    }

    public static class Type {
        public static final RecipeType<ItemTransformationRecipe> INSTANCE = RecipeType.simple(Nautec.rl(NAME));

        private Type() {
        }
    }
}
