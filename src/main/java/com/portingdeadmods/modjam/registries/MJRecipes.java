package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.recipes.MixingRecipe;
import com.portingdeadmods.modjam.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.modjam.content.recipes.ItemEtchingRecipe;
import com.portingdeadmods.modjam.content.recipes.ItemTransformationRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.lang.invoke.SerializedLambda;

public final class MJRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ModJam.MODID);

    static {
        SERIALIZERS.register(AquaticCatalystChannelingRecipe.NAME, () -> AquaticCatalystChannelingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(ItemTransformationRecipe.NAME, () -> ItemTransformationRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(ItemEtchingRecipe.NAME, () -> ItemEtchingRecipe.Serializer.INSTANCE);
        SERIALIZERS.register(MixingRecipe.NAME, () -> MixingRecipe.Serializer.INSTANCE);
    }

}
