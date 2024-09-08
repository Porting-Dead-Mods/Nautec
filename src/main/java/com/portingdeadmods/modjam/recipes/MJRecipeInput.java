package com.portingdeadmods.modjam.recipes;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record MJRecipeInput(List<ItemStack> items) implements RecipeInput {

    public MJRecipeInput(ItemStack ...items) {
        this(List.of(items));
    }

    @Override
    public @NotNull ItemStack getItem(int i) {
        return items.get(i);
    }

    @Override
    public int size() {
        return items.size();
    }
}
