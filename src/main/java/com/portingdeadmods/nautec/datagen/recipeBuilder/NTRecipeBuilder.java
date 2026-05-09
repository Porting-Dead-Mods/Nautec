package com.portingdeadmods.nautec.datagen.recipeBuilder;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface NTRecipeBuilder extends RecipeBuilder {
    List<Ingredient> getIngredients();

    String getName();

    static String ingredientPathSuffix(Ingredient ingredient) {
        StringBuilder out = new StringBuilder();
        for (Ingredient.Value value : ingredient.getValues()) {
            for (ItemStack stack : value.getItems()) {
                out.append('_').append(BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().replace(':', '-'));
            }
        }
        return out.toString();
    }

    @Override
    default void save(RecipeOutput recipeOutput) {
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : getIngredients()) {
            for (Ingredient.Value value : ingredient.getValues()) {
                if (value instanceof Ingredient.ItemValue(ItemStack item)) {
                    builder.append(BuiltInRegistries.ITEM.getKey(item.getItem()).getPath()).append("_");
                } else if (value instanceof Ingredient.TagValue(TagKey<Item> tag)) {
                    builder.append(tag.location().getPath()).append("_");
                }
            }
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
