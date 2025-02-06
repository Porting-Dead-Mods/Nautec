package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.recipes.inputs.BacteriaRecipeInput;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record BacteriaIncubationRecipe(ResourceKey<Bacteria> bacteria, Ingredient nutrient, IntRange growth, float consumeChance) implements Recipe<BacteriaRecipeInput> {
    public static final String NAME = "bacteria_incubation";
    public static final RecipeType<BacteriaIncubationRecipe> TYPE = RecipeType.simple(Nautec.rl(NAME));

    @Override
    public boolean matches(BacteriaRecipeInput input, Level level) {
        return input.input().is(bacteria) && nutrient.test(input.catalyst());
    }

    @Override
    public ItemStack assemble(BacteriaRecipeInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    public static final class Serializer implements RecipeSerializer<BacteriaIncubationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final MapCodec<BacteriaIncubationRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Bacteria.BACTERIA_TYPE_CODEC.fieldOf("bacteria").forGetter(BacteriaIncubationRecipe::bacteria),
                Ingredient.CODEC.fieldOf("nutrient").forGetter(BacteriaIncubationRecipe::nutrient),
                IntRange.MAP_CODEC.fieldOf("growth").forGetter(BacteriaIncubationRecipe::growth),
                Codec.FLOAT.fieldOf("consume_chance").forGetter(BacteriaIncubationRecipe::consumeChance)
        ).apply(inst, BacteriaIncubationRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BacteriaIncubationRecipe> STREAM_CODEC = StreamCodec.composite(
                Bacteria.BACTERIA_TYPE_STREAM_CODEC,
                BacteriaIncubationRecipe::bacteria,
                Ingredient.CONTENTS_STREAM_CODEC,
                BacteriaIncubationRecipe::nutrient,
                IntRange.STREAM_CODEC,
                BacteriaIncubationRecipe::growth,
                ByteBufCodecs.FLOAT,
                BacteriaIncubationRecipe::consumeChance,
                BacteriaIncubationRecipe::new
        );

        private Serializer() {
        }

        @Override
        public MapCodec<BacteriaIncubationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BacteriaIncubationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
