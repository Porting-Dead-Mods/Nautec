package com.portingdeadmods.modjam.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record AquaticCatalystChannelingRecipe(Ingredient ingredient, int powerAmount, float purity, int duration) implements Recipe<SingleRecipeInput> {
    public static final String NAME = "aquatic_catalyst_channeling";

    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput, @NotNull Level level) {
        return ingredient.test(recipeInput.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return ItemStack.EMPTY;
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
        return NonNullList.of(Ingredient.EMPTY, ingredient);
    }

    public static class Serializer implements RecipeSerializer<AquaticCatalystChannelingRecipe> {
        public static final AquaticCatalystChannelingRecipe.Serializer INSTANCE = new AquaticCatalystChannelingRecipe.Serializer();
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

        private Serializer() {
        }

        @Override
        public @NotNull MapCodec<AquaticCatalystChannelingRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, AquaticCatalystChannelingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Type implements RecipeType<AquaticCatalystChannelingRecipe> {
        public static final AquaticCatalystChannelingRecipe.Type INSTANCE = new AquaticCatalystChannelingRecipe.Type();

        private Type() {
        }

        @Override
        public String toString() {
            return AquaticCatalystChannelingRecipe.NAME;
        }
    }
}
