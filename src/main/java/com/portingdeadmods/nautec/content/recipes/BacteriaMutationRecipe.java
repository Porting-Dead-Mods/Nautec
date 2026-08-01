package com.portingdeadmods.nautec.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.recipes.inputs.BacteriaRecipeInput;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record BacteriaMutationRecipe(ResourceKey<Bacteria> inputBacteria, ResourceKey<Bacteria> resultBacteria,
                                     Ingredient catalyst, float chance) implements Recipe<BacteriaRecipeInput> {
    public static final String NAME = "bacteria_mutation";
    public static final RecipeType<BacteriaMutationRecipe> TYPE = RecipeType.simple(Nautec.rl("bacteria_mutation"));


    /**
     * <b><i>THIS METHOD SHOULD ONLY BE USED CLIENT SIDE :3</i></b>
     */
    public ItemStack getInputDish() {
        return BacteriaHelper.getMaxStatDish(inputBacteria, Minecraft.getInstance().level.registryAccess());
    }

    /**
     * <b><i>THIS METHOD SHOULD ONLY BE USED CLIENT SIDE :3</i></b>
     */
    public ItemStack getOutputDish() {
        return BacteriaHelper.getMaxStatDish(resultBacteria, Minecraft.getInstance().level.registryAccess());
    }

    @Override
    public boolean matches(BacteriaRecipeInput input, Level level) {
        return input.input().is(inputBacteria) && catalyst.test(input.catalyst());
    }

    @Override
    public ItemStack assemble(BacteriaRecipeInput input) {
        return ItemStack.EMPTY;
    }

    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public RecipeSerializer<? extends Recipe<BacteriaRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<BacteriaRecipeInput>> getType() {
        return TYPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public static final class Serializer {
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
        public static final RecipeSerializer<BacteriaMutationRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);

        private Serializer() {
        }
    }
}
