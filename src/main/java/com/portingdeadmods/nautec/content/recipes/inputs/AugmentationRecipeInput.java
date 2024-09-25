package com.portingdeadmods.nautec.content.recipes.inputs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record AugmentationRecipeInput(List<ItemStack> ingredients, int robotArms) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return ingredients.get(index);
    }

    @Override
    public int size() {
        return ingredients.size();
    }
}
