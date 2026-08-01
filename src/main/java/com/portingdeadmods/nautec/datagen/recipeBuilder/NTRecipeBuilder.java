package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public interface NTRecipeBuilder extends RecipeBuilder {
    List<Ingredient> getIngredients();

    String getName();

    Item getResult();

    static String ingredientPathSuffix(Ingredient ingredient) {
        StringBuilder out = new StringBuilder();
        ingredient.getValues().unwrap()
                .ifLeft(tag -> out.append('_').append(tag.location().getPath().replace(':', '-')))
                .ifRight(holders -> {
                    for (Holder<Item> holder : holders) {
                        out.append('_').append(BuiltInRegistries.ITEM.getKey(holder.value()).getPath().replace(':', '-'));
                    }
                });
        return out.toString();
    }

    @Override
    default ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Nautec.rl(getName()));
    }

    default void save(RecipeOutput recipeOutput, Identifier id) {
        save(recipeOutput, ResourceKey.create(Registries.RECIPE, id));
    }

    @Override
    default void save(RecipeOutput recipeOutput) {
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : getIngredients()) {
            ingredient.getValues().unwrap()
                    .ifLeft(tag -> builder.append(tag.location().getPath()).append("_"))
                    .ifRight(holders -> {
                        for (Holder<Item> holder : holders) {
                            builder.append(BuiltInRegistries.ITEM.getKey(holder.value()).getPath()).append("_");
                        }
                    });
        }
        Item result = getResult();
        if (result != Items.AIR) {
            builder.append("to_").append(BuiltInRegistries.ITEM.getKey(result).getPath());
        } else {
            builder.deleteCharAt(builder.length() - 1);
        }
        save(recipeOutput, Nautec.rl(getName() + "/" + builder));
    }
}
