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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@JeiPlugin
public class NTJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "jei_plugin");
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

        registration.addRecipeCategories();
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Minecraft.getInstance().level;
        RecipeManager recipeManager = level.getRecipeManager();
        RegistryAccess registryAccess = level.registryAccess();

        List<ItemTransformationRecipe> transformationRecipes = recipeManager.getAllRecipesFor(ItemTransformationRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<AquaticCatalystChannelingRecipe> channelingRecipes = recipeManager.getAllRecipesFor(AquaticCatalystChannelingRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<ItemEtchingRecipe> etchingRecipes = recipeManager.getAllRecipesFor(ItemEtchingRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<MixingRecipe> mixingRecipes = recipeManager.getAllRecipesFor(MixingRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<AugmentationRecipe> augmentationRecipes = recipeManager.getAllRecipesFor(AugmentationRecipe.Type.INSTANCE)
                .stream().map(RecipeHolder::value).toList();

        List<BacteriaMutationRecipe> mutationRecipes = recipeManager.getAllRecipesFor(BacteriaMutationRecipe.TYPE)
                .stream().map(RecipeHolder::value).toList();

        List<BacteriaIncubationRecipe> incubationRecipes = recipeManager.getAllRecipesFor(BacteriaIncubationRecipe.TYPE)
                .stream().map(RecipeHolder::value).toList();

        Registry<Bacteria> registry = registryAccess.registryOrThrow(NTRegistries.BACTERIA_KEY);
        List<BioReactorCategory.BioReactorRecipe> bioReactorRecipes = registry.entrySet().stream()
                .map(entry -> new BioReactorCategory.BioReactorRecipe(entry.getKey(), entry.getValue().resource()))
                .filter(recipe -> !(recipe.bacteria().equals(NTBacterias.EMPTY) || recipe.resource().isEmpty()))
                .toList();

        Map<ResourceKey<Block>, BacteriaObtainValue> dataMap = BuiltInRegistries.BLOCK.getDataMap(NTDataMaps.BACTERIA_OBTAINING);
        List<BacteriaGraftingCategory.GraftingRecipe> graftingRecipes = dataMap.entrySet().stream()
                .map(entry -> new BacteriaGraftingCategory.GraftingRecipe(registryAccess.holderOrThrow(entry.getKey()).value(), entry.getValue()))
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

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.AQUATIC_CATALYST.get()),
                AquaticCatalystChannelingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTFluids.ETCHING_ACID.getBucket()),
                ItemEtchingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.MIXER.get()),
                MixingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.AUGMENTATION_STATION.get()),
                AugmentationRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.MUTATOR.get()),
                BacteriaMutationsCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.INCUBATOR.get()),
                BacteriaIncubationCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.BIO_REACTOR.get()),
                BioReactorCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTItems.GRAFTING_TOOL.get()),
                BacteriaGraftingCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(NTItems.PETRI_DISH.get()),
                BacteriaGraftingCategory.RECIPE_TYPE);
    }

}
