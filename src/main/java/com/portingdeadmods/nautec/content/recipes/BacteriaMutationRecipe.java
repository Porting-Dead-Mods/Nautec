package com.portingdeadmods.nautec.content.recipes;

import com.ibm.icu.impl.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.bacteria.BacteriaMutation;
import com.portingdeadmods.nautec.content.recipes.inputs.BacteriaMutationRecipeInput;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * <b><i>THIS CLASS SHOULD ONLY BE USED CLIENT SIDE :3</i></b>
 */
public record BacteriaMutationRecipe(ResourceKey<Bacteria> inputBacteria, ResourceKey<Bacteria> resultBacteria,
                                     Ingredient catalyst, float chance) implements Recipe<BacteriaMutationRecipeInput> {
    public static final String NAME = "bacteria_mutation";
    public static final RecipeType<BacteriaMutationRecipe> TYPE = RecipeType.simple(Nautec.rl("bacteria_mutation"));

    @Override
    public boolean matches(BacteriaMutationRecipeInput input, Level level) {
        return input.input().is(inputBacteria) && catalyst.test(input.catalyst());
    }

    @Override
    public ItemStack assemble(BacteriaMutationRecipeInput input, HolderLookup.Provider registries) {
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

    public static final class Serializer implements RecipeSerializer<BacteriaMutationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final MapCodec<BacteriaMutationRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Bacteria.BACTERIA_TYPE_CODEC.fieldOf("input_bacteria").forGetter(BacteriaMutationRecipe::inputBacteria),
                Bacteria.BACTERIA_TYPE_CODEC.fieldOf("result_bacteria").forGetter(BacteriaMutationRecipe::resultBacteria),
                Ingredient.CODEC.fieldOf("catalyst").forGetter(BacteriaMutationRecipe::catalyst),
                Codec.FLOAT.fieldOf("chance").forGetter(BacteriaMutationRecipe::chance)
        ).apply(inst, BacteriaMutationRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BacteriaMutationRecipe> STREAM_CODEC = StreamCodec.composite(
                Bacteria.BACTERIA_TYPE_STREAM_CODEC,
                BacteriaMutationRecipe::inputBacteria,
                Bacteria.BACTERIA_TYPE_STREAM_CODEC,
                BacteriaMutationRecipe::resultBacteria,
                Ingredient.CONTENTS_STREAM_CODEC,
                BacteriaMutationRecipe::catalyst,
                ByteBufCodecs.FLOAT,
                BacteriaMutationRecipe::chance,
                BacteriaMutationRecipe::new
        );

        private Serializer() {
        }

        @Override
        public MapCodec<BacteriaMutationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BacteriaMutationRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

