package com.portingdeadmods.modjam.content.recipes.inputs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record AugmentationRecipeInput(ItemStack ingredient, List<ItemStack> robotArms) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? ingredient : robotArms.get(index - 1);
    }

    @Override
    public int size() {
        return robotArms().size() + 1;
    }
}
