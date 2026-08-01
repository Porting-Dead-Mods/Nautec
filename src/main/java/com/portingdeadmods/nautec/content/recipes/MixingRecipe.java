package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.inputs.MixingRecipeInput;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record MixingRecipe(List<IngredientWithCount> ingredients, Optional<FluidStackTemplate> fluidIngredientTemplate, Optional<ItemStackTemplate> resultTemplate,
                           Optional<FluidStackTemplate> fluidResultTemplate, int duration) implements Recipe<MixingRecipeInput> {
    public static final String NAME = "mixing";

    public MixingRecipe {
        ingredients = ingredients != null ? ingredients : new ArrayList<>();
        fluidIngredientTemplate = fluidIngredientTemplate != null ? fluidIngredientTemplate : Optional.empty();
        resultTemplate = resultTemplate != null ? resultTemplate : Optional.empty();
        fluidResultTemplate = fluidResultTemplate != null ? fluidResultTemplate : Optional.empty();
    }

    public @NotNull ItemStack result() {
        return resultTemplate.map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
    }

    public @NotNull FluidStack fluidIngredient() {
        return fluidIngredientTemplate.map(FluidStackTemplate::create).orElse(FluidStack.EMPTY);
    }

    public @NotNull FluidStack fluidResult() {
        return fluidResultTemplate.map(FluidStackTemplate::create).orElse(FluidStack.EMPTY);
    }

    @Override
    public boolean matches(@NotNull MixingRecipeInput recipeInput, @NotNull Level level) {
        FluidStack fluidIngredient = fluidIngredient();
        boolean fluidMatches = recipeInput.fluidStack() != null && !fluidIngredient.isEmpty() &&
                recipeInput.fluidStack().is(fluidIngredient.getFluid()) &&
                recipeInput.fluidStack().getAmount() >= fluidIngredient.getAmount();
        boolean itemsMatch = RecipeUtils.compareItems(recipeInput.items(), this.ingredients);
        return itemsMatch && fluidMatches;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MixingRecipeInput input) {
        return result();
    }

    public @NotNull ItemStack getResultItem(HolderLookup.@Nullable Provider registries) {
        return result();
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
    public @NotNull RecipeSerializer<? extends Recipe<MixingRecipeInput>> getSerializer() {
        return MixingRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<MixingRecipeInput>> getType() {
        return MixingRecipe.Type.INSTANCE;
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
        return RecipeUtils.listToNonNullList(RecipeUtils.iWCToIngredientsSaveCount(ingredients));
    }

    public @NotNull NonNullList<IngredientWithCount> getIngredientsWithCount() {
        return RecipeUtils.listToNonNullList(ingredients);
    }

    public static class Serializer {
        private static final MapCodec<MixingRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                IngredientWithCount.CODEC.listOf().fieldOf("ingredients").forGetter(MixingRecipe::ingredients),
                FluidStackTemplate.CODEC.optionalFieldOf("fluid_ingredient").forGetter(MixingRecipe::fluidIngredientTemplate),
                ItemStackTemplate.CODEC.optionalFieldOf("result").forGetter(MixingRecipe::resultTemplate),
                FluidStackTemplate.CODEC.optionalFieldOf("fluid_result").forGetter(MixingRecipe::fluidResultTemplate),
                Codec.INT.fieldOf("duration").forGetter(MixingRecipe::duration)
        ).apply(builder, MixingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, MixingRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC.apply(ByteBufCodecs.list()),
                MixingRecipe::ingredients,
                ByteBufCodecs.optional(FluidStackTemplate.STREAM_CODEC),
                MixingRecipe::fluidIngredientTemplate,
                ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC),
                MixingRecipe::resultTemplate,
                ByteBufCodecs.optional(FluidStackTemplate.STREAM_CODEC),
                MixingRecipe::fluidResultTemplate,
                ByteBufCodecs.INT,
                MixingRecipe::duration,
                MixingRecipe::new
        );
        public static final RecipeSerializer<MixingRecipe> INSTANCE = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

        private Serializer() {
        }
    }

    public static class Type {
        public static final RecipeType<MixingRecipe> INSTANCE = RecipeType.simple(Nautec.rl(NAME));

        private Type() {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MixingRecipe that)) return false;
        return duration == that.duration && Objects.equals(resultTemplate, that.resultTemplate) && Objects.equals(fluidResultTemplate, that.fluidResultTemplate) && Objects.equals(fluidIngredientTemplate, that.fluidIngredientTemplate) && Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients, fluidIngredientTemplate, resultTemplate, fluidResultTemplate, duration);
    }
}
