package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
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

public record AugmentationRecipe(Item augmentItem, String desc, List<IngredientWithCount> ingredients, AugmentType<?> resultAugment) implements Recipe<AugmentationRecipeInput> {
    public static final String NAME = "augmentation";

    @Override
    public boolean matches(@NotNull AugmentationRecipeInput recipeInput, @NotNull Level level) {
        return RecipeUtils.compareItems(recipeInput.ingredients(), ingredients);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull AugmentationRecipeInput input) {
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
    public @NotNull RecipeSerializer<? extends Recipe<AugmentationRecipeInput>> getSerializer() {
        return AugmentationRecipe.Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<AugmentationRecipeInput>> getType() {
        return AugmentationRecipe.Type.INSTANCE;
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

    public static class Serializer {
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
        public static final RecipeSerializer<AugmentationRecipe> INSTANCE = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

        private Serializer() {
        }
    }

    public static class Type {
        public static final RecipeType<AugmentationRecipe> INSTANCE = RecipeType.simple(Nautec.rl(NAME));

        private Type() {
        }
    }
}
