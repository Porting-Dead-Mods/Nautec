package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.recipes.ItemTransformationRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MJRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ModJam.MODID);

    static {
        SERIALIZERS.register(ItemTransformationRecipe.NAME, () -> ItemTransformationRecipe.Serializer.INSTANCE);
    }

}
