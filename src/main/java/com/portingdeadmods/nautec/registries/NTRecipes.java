package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NTRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Nautec.MODID);

    static {
        SERIALIZERS.register(AquaticCatalystChannelingRecipe.NAME, () -> AquaticCatalystChannelingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(ItemTransformationRecipe.NAME, () -> ItemTransformationRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(ItemEtchingRecipe.NAME, () -> ItemEtchingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(MixingRecipe.NAME, () -> MixingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(AugmentationRecipe.NAME, () -> AugmentationRecipe.Serializer.INSTANCE);
    }

}
