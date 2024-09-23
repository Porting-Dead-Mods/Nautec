package com.portingdeadmods.nautec.content.recipes.inputs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record AugmentationRecipeInput(ItemStack ingredient, int robotArms) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? ingredient : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 1;
    }
}
