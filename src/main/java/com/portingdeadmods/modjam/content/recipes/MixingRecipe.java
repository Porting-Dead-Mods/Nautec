package com.portingdeadmods.modjam.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.modjam.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.modjam.content.recipes.utils.MixingRecipeInput;
import com.portingdeadmods.modjam.content.recipes.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record MixingRecipe(List<IngredientWithCount> ingredients, FluidStack fluidIngredient, ItemStack result,
                           FluidStack fluidResult, int duration) implements Recipe<MixingRecipeInput> {
    public static final String NAME = "mixing";

    @Override
    public boolean matches(@NotNull MixingRecipeInput recipeInput, @NotNull Level level) {
        return RecipeUtils.compareItems(recipeInput.items(), this.ingredients) && FluidStack.isSameFluid(recipeInput.fluidStack(), fluidIngredient);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MixingRecipeInput input, HolderLookup.@NotNull Provider registries) {
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
        return ItemTransformationRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ItemTransformationRecipe.Type.INSTANCE;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return RecipeUtils.listToNonNullList(RecipeUtils.iWCToIngredientsSaveCount(ingredients));
    }

    public @NotNull NonNullList<IngredientWithCount> getIngredientsWithCount() {
        return RecipeUtils.listToNonNullList(ingredients);
    }

    public static class Serializer implements RecipeSerializer<MixingRecipe> {
        public static final MixingRecipe.Serializer INSTANCE = new MixingRecipe.Serializer();
        private static final MapCodec<MixingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                IngredientWithCount.CODEC.listOf().fieldOf("ingredients").forGetter(MixingRecipe::ingredients),
                FluidStack.OPTIONAL_CODEC.fieldOf("fluid_ingredient").forGetter(MixingRecipe::fluidIngredient),
                ItemStack.CODEC.fieldOf("result").forGetter(MixingRecipe::result),
                FluidStack.OPTIONAL_CODEC.fieldOf("fluid_result").forGetter(MixingRecipe::fluidResult),
                Codec.INT.fieldOf("duration").forGetter(MixingRecipe::duration)
        ).apply(builder, MixingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, MixingRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC.apply(ByteBufCodecs.list()),
                MixingRecipe::ingredients,
                FluidStack.OPTIONAL_STREAM_CODEC,
                MixingRecipe::fluidIngredient,
                ItemStack.STREAM_CODEC,
                MixingRecipe::result,
                FluidStack.OPTIONAL_STREAM_CODEC,
                MixingRecipe::fluidResult,
                ByteBufCodecs.INT,
                MixingRecipe::duration,
                MixingRecipe::new
        );

        private Serializer() {
        }

        @Override
        public @NotNull MapCodec<MixingRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, MixingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Type implements RecipeType<MixingRecipe> {
        public static final MixingRecipe.Type INSTANCE = new MixingRecipe.Type();

        private Type() {
        }

        @Override
        public String toString() {
            return MixingRecipe.NAME;
        }
    }
}

