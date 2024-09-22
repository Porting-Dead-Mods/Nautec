package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.recipes.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MJRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ModJam.MODID);

    static {
        SERIALIZERS.register(AquaticCatalystChannelingRecipe.NAME, () -> AquaticCatalystChannelingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(ItemTransformationRecipe.NAME, () -> ItemTransformationRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(ItemEtchingRecipe.NAME, () -> ItemEtchingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(MixingRecipe.NAME, () -> MixingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(AugmentationRecipe.NAME, () -> AugmentationRecipe.Serializer.INSTANCE);
    }

}
