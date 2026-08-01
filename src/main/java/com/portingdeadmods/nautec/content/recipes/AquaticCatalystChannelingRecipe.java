package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
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

import java.util.List;

public record AquaticCatalystChannelingRecipe(Ingredient ingredient, int powerAmount, float purity, int duration) implements Recipe<SingleRecipeInput> {
    public static final String NAME = "aquatic_catalyst_channeling";

    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput, @NotNull Level level) {
        return ingredient.test(recipeInput.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input) {
        return ItemStack.EMPTY;
    }

    public @NotNull ItemStack getResultItem(HolderLookup.@Nullable Provider registries) {
        return ItemStack.EMPTY;
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
        return RecipeUtils.listToNonNullList(List.of(ingredient));
    }

    public static class Serializer {
        private static final MapCodec<AquaticCatalystChannelingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(AquaticCatalystChannelingRecipe::ingredient),
                Codec.INT.fieldOf("power_amount").forGetter(AquaticCatalystChannelingRecipe::powerAmount),
                Codec.FLOAT.fieldOf("purity").forGetter(AquaticCatalystChannelingRecipe::purity),
                Codec.INT.fieldOf("duration").forGetter(AquaticCatalystChannelingRecipe::duration)
        ).apply(builder, AquaticCatalystChannelingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, AquaticCatalystChannelingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                AquaticCatalystChannelingRecipe::ingredient,
                ByteBufCodecs.INT,
                AquaticCatalystChannelingRecipe::powerAmount,
                ByteBufCodecs.FLOAT,
                AquaticCatalystChannelingRecipe::purity,
                ByteBufCodecs.INT,
                AquaticCatalystChannelingRecipe::duration,
                AquaticCatalystChannelingRecipe::new
        );
        public static final RecipeSerializer<AquaticCatalystChannelingRecipe> INSTANCE = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

        private Serializer() {
        }
    }

    public static class Type {
        public static final RecipeType<AquaticCatalystChannelingRecipe> INSTANCE = RecipeType.simple(Nautec.rl(NAME));

        private Type() {
        }
    }
}
