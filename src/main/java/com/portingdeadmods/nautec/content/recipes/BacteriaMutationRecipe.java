package com.portingdeadmods.nautec.content.recipes;

import com.ibm.icu.impl.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.bacteria.BacteriaMutation;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * <b><i>THIS CLASS SHOULD ONLY BE USED CLIENT SIDE :3</i></b>
 */
public class BacteriaMutationRecipe {
    public static final String NAME = "bacteria_mutation";
    private static final RegistryAccess reg = Minecraft.getInstance().level.registryAccess();

    public final ItemStack INPUT_PETRIDISH;
    public final ItemStack OUTPUT_PETRIDISH;
    public final Item catalyst;
    public final int chance;

    public BacteriaMutationRecipe(ResourceKey<Bacteria> input, ResourceKey<Bacteria> output, Item catalyst, int chance) {
        this.catalyst = catalyst;
        this.chance = chance;

        this.INPUT_PETRIDISH = new ItemStack(NTItems.PETRI_DISH.get(), 1);
        INPUT_PETRIDISH.set(NTDataComponents.ANALYZED, false);
        INPUT_PETRIDISH.set(NTDataComponents.BACTERIA, new ComponentBacteriaStorage(
                new BacteriaInstance(input, reg)
        ));

        this.OUTPUT_PETRIDISH = new ItemStack(NTItems.PETRI_DISH.get(), 1);
        OUTPUT_PETRIDISH.set(NTDataComponents.ANALYZED, false);
        OUTPUT_PETRIDISH.set(NTDataComponents.BACTERIA, new ComponentBacteriaStorage(
                new BacteriaInstance(output, reg)
        ));
    }
}

