package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.augments.AugmentType;
import com.portingdeadmods.nautec.content.recipes.inputs.AugmentationRecipeInput;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import com.portingdeadmods.nautec.utils.codec.AugmentCodecs;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record AugmentationRecipe(Item augmentItem, String desc, List<IngredientWithCount> ingredients, AugmentType<?> resultAugment) implements Recipe<AugmentationRecipeInput> {
    public static final String NAME = "augmentation";

    @Override
    public boolean matches(@NotNull AugmentationRecipeInput recipeInput, @NotNull Level level) {
        return RecipeUtils.compareItems(recipeInput.ingredients(), ingredients);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull AugmentationRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@Nullable Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return AugmentationRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return AugmentationRecipe.Type.INSTANCE;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return RecipeUtils.listToNonNullList(RecipeUtils.iWCToIngredientsSaveCount(ingredients));
    }

    public static class Serializer implements RecipeSerializer<AugmentationRecipe> {
        public static final AugmentationRecipe.Serializer INSTANCE = new AugmentationRecipe.Serializer();
        private static final MapCodec<AugmentationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(
                CodecUtils.ITEM_CODEC.fieldOf("augmentItem").forGetter(AugmentationRecipe::augmentItem),
                Codec.STRING.fieldOf("desc").orElse("").forGetter(AugmentationRecipe::desc),
                IngredientWithCount.CODEC.listOf().fieldOf("ingredients").forGetter(AugmentationRecipe::ingredients),
                AugmentCodecs.AUGMENT_TYPE_CODEC.fieldOf("resultAugment").forGetter(AugmentationRecipe::resultAugment)
        ).apply(builder, AugmentationRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, AugmentationRecipe> STREAM_CODEC = StreamCodec.composite(
                CodecUtils.ITEM_STREAM_CODEC,
                AugmentationRecipe::augmentItem,
                ByteBufCodecs.STRING_UTF8,
                AugmentationRecipe::desc,
                IngredientWithCount.STREAM_CODEC.apply(ByteBufCodecs.list()),
                AugmentationRecipe::ingredients,
                AugmentCodecs.AUGMENT_TYPE_STREAM_CODEC,
                AugmentationRecipe::resultAugment,
                AugmentationRecipe::new
        );

        private Serializer() {
        }

        @Override
        public @NotNull MapCodec<AugmentationRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, AugmentationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Type implements RecipeType<AugmentationRecipe> {
        public static final AugmentationRecipe.Type INSTANCE = new AugmentationRecipe.Type();

        private Type() {
        }

        @Override
        public String toString() {
            return AugmentationRecipe.NAME;
        }
    }
}

