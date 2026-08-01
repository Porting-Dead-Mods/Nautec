package com.portingdeadmods.nautec.compat.jei;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.compat.jei.categories.*;
import com.portingdeadmods.nautec.content.recipes.*;
import com.portingdeadmods.nautec.data.NTDataMaps;
import com.portingdeadmods.nautec.data.maps.BacteriaObtainValue;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@JeiPlugin
public class NTJeiPlugin implements IModPlugin {

    @Override
    public @NotNull Identifier getPluginUid() {
        return Nautec.rl("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ItemTransformationRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new AquaticCatalystChannelingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new ItemEtchingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new MixingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new AugmentationRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new BacteriaMutationsCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new BacteriaIncubationCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new BioReactorCategory(
                registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new BacteriaGraftingCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        RegistryAccess registryAccess = level.registryAccess();

        List<ItemTransformationRecipe> transformationRecipes = recipesFor(ItemTransformationRecipe.Type.INSTANCE);
        List<AquaticCatalystChannelingRecipe> channelingRecipes = recipesFor(AquaticCatalystChannelingRecipe.Type.INSTANCE);
        List<ItemEtchingRecipe> etchingRecipes = recipesFor(ItemEtchingRecipe.Type.INSTANCE);
        List<MixingRecipe> mixingRecipes = recipesFor(MixingRecipe.Type.INSTANCE);
        List<AugmentationRecipe> augmentationRecipes = recipesFor(AugmentationRecipe.Type.INSTANCE);
        List<BacteriaMutationRecipe> mutationRecipes = recipesFor(BacteriaMutationRecipe.TYPE);
        List<BacteriaIncubationRecipe> incubationRecipes = recipesFor(BacteriaIncubationRecipe.TYPE);

        Registry<Bacteria> registry = registryAccess.lookupOrThrow(NTRegistries.BACTERIA_KEY);
        List<BioReactorCategory.BioReactorRecipe> bioReactorRecipes = registry.entrySet().stream()
                .map(entry -> new BioReactorCategory.BioReactorRecipe(entry.getKey(), entry.getValue().resource()))
                .filter(recipe -> !(recipe.bacteria().equals(NTBacterias.EMPTY) || recipe.resource().isEmpty()))
                .toList();

        Map<ResourceKey<Block>, BacteriaObtainValue> dataMap = BuiltInRegistries.BLOCK.getDataMap(NTDataMaps.BACTERIA_OBTAINING);
        List<BacteriaGraftingCategory.GraftingRecipe> graftingRecipes = dataMap.entrySet().stream()
                .map(entry -> new BacteriaGraftingCategory.GraftingRecipe(BuiltInRegistries.BLOCK.getValueOrThrow(entry.getKey()), entry.getValue()))
                .toList();

        registration.addRecipes(AugmentationRecipeCategory.RECIPE_TYPE, augmentationRecipes);
        registration.addRecipes(ItemTransformationRecipeCategory.RECIPE_TYPE, transformationRecipes);
        registration.addRecipes(AquaticCatalystChannelingRecipeCategory.RECIPE_TYPE, channelingRecipes);
        registration.addRecipes(ItemEtchingRecipeCategory.RECIPE_TYPE, etchingRecipes);
        registration.addRecipes(MixingRecipeCategory.RECIPE_TYPE, mixingRecipes);
        registration.addRecipes(BacteriaMutationsCategory.RECIPE_TYPE, mutationRecipes);
        registration.addRecipes(BacteriaIncubationCategory.RECIPE_TYPE, incubationRecipes);
        registration.addRecipes(BioReactorCategory.RECIPE_TYPE, bioReactorRecipes);
        registration.addRecipes(BacteriaGraftingCategory.RECIPE_TYPE, graftingRecipes);

        for (AugmentationRecipe recipe : augmentationRecipes) {
            registration.addIngredientInfo(recipe.augmentItem().getDefaultInstance(), VanillaTypes.ITEM_STACK, Component.translatable(recipe.desc()));
        }

        registration.addIngredientInfo(NTItems.PRISMARINE_CRYSTAL_SHARD.toStack(), VanillaTypes.ITEM_STACK,
                Component.literal("Prismarine Crystal Shards are pristine crystals, capable of channeling power like no other material. They can be obtained by breaking a prismarine crystal using an Aquarine Steel Pickaxe with its ability enabled."));

        registration.addIngredientInfo(List.of(NTItems.BROKEN_WHISK.toStack(), NTItems.BURNT_COIL.toStack(), NTItems.ANCIENT_VALVE.toStack(), NTItems.RUSTY_GEAR.toStack()), VanillaTypes.ITEM_STACK, Component.literal("These ancient machine components can be found in chests and are dropped by underwater mobs"));
    }

    private static <I extends RecipeInput, R extends Recipe<I>> List<R> recipesFor(RecipeType<R> type) {
        return syncedRecipes().byType(type).stream().map(RecipeHolder::value).toList();
    }

    private static RecipeMap syncedRecipes() {
        try {
            Class<?> internal = Class.forName("mezz.jei.common.Internal");
            return (RecipeMap) internal.getMethod("getClientSyncedRecipes").invoke(null);
        } catch (ReflectiveOperationException | ClassCastException e) {
            Nautec.LOGGER.error("Failed to access JEI synced recipes", e);
            return RecipeMap.EMPTY;
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(AquaticCatalystChannelingRecipeCategory.RECIPE_TYPE,
                new ItemStack(NTBlocks.AQUATIC_CATALYST.get()));
        registration.addCraftingStation(ItemEtchingRecipeCategory.RECIPE_TYPE,
                new ItemStack(NTFluids.ETCHING_ACID.getBucket()));
        registration.addCraftingStation(MixingRecipeCategory.RECIPE_TYPE,
                new ItemStack(NTBlocks.MIXER.get()));
        registration.addCraftingStation(AugmentationRecipeCategory.RECIPE_TYPE,
                new ItemStack(NTBlocks.AUGMENTATION_STATION.get()));
        registration.addCraftingStation(BacteriaMutationsCategory.RECIPE_TYPE,
                new ItemStack(NTBlocks.MUTATOR.get()));
        registration.addCraftingStation(BacteriaIncubationCategory.RECIPE_TYPE,
                new ItemStack(NTBlocks.INCUBATOR.get()));
        registration.addCraftingStation(BioReactorCategory.RECIPE_TYPE,
                new ItemStack(NTBlocks.BIO_REACTOR.get()));
        registration.addCraftingStation(BacteriaGraftingCategory.RECIPE_TYPE,
                new ItemStack(NTItems.GRAFTING_TOOL.get()),
                new ItemStack(NTItems.PETRI_DISH.get()));
    }

}
