package com.portingdeadmods.modjam.datagen.recipeBuilder;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public interface MJRecipeBuilder extends RecipeBuilder {
    @Override
    default void save(RecipeOutput recipeOutput) {
        ResourceLocation defaultId = getDefaultRecipeId(getResult());
        save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ModJam.MODID, defaultId.getPath()));
    }

    static ResourceLocation getDefaultRecipeId(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
    }
}
