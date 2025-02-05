package com.portingdeadmods.nautec.content.recipes.inputs;

import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public record BacteriaMutationRecipeInput(BacteriaInstance input, ItemStack catalyst) implements RecipeInput {
    @Override
    public @NotNull ItemStack getItem(int index) {
        return catalyst;
    }

    @Override
    public int size() {
        return 1;
    }
}
